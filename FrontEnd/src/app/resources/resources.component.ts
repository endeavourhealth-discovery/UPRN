import { Component, OnInit } from '@angular/core';
import {ResourcesService} from './resources.service';
import {PersonFindDialogComponent} from '../person-find/person-find-dialog/person-find-dialog.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Service} from '../models/Service';
import {Person} from '../models/Person';
import { LinqService } from 'ng2-linq';
import {ResourceType} from '../models/ResourceType';
import {Patient} from '../models/Patient';
import {LoggerService} from 'eds-angular4';
import {ViewerComponent} from './viewer/viewer.component';
import {ServicePatientResource} from '../models/Resource';
import { DateHelper} from '../pipes/cui';
import {System} from '../models/System';
import {ResourceId} from '../models/ResourceId';

@Component({
  selector: 'app-resources-component',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  private resourceMap: any;
  private serviceMap: any;
  private systemMap: any;

  protected resourceTypes: ResourceType[] = [];
  protected resourceFilter: string[];

  protected person: Person;
  protected patients: Patient[] = [];
  protected patientFilter: string[];

  protected patientResourceList: ServicePatientResource[] = [];
  protected clinicalResourceList: ServicePatientResource[] = [];

  protected highlight: ServicePatientResource;
  protected lastHighlight: ServicePatientResource;

  constructor(protected logger: LoggerService,
              protected linq: LinqService,
              protected modal: NgbModal,
              protected resourceService: ResourcesService) {
    this.getResourceTypes();
  }

  ngOnInit() {
  }

  /** RESOURCE TYPES **/
  private getResourceTypes(): void {
    const vm = this;
    vm.resourceService.getResourceTypes()
      .subscribe(
        (result) => vm.processResourceTypes(result),
        (error) => vm.logger.error(error)
      );
  }

  private processResourceTypes(resourceTypes: ResourceType[]) {
    this.resourceTypes = resourceTypes;
    this.resourceMap = {};
    for (const resourceType of resourceTypes)
      this.resourceMap[resourceType.id] = resourceType.name;
  }

  /** PERSON FIND **/
  private findPerson() {
    PersonFindDialogComponent.open(this.modal)
      .result.then(
      (result) => this.loadPerson(result),
      (error) => this.logger.error(error)
    );
  }

  /** INITIAL DATA LOAD **/
  private loadPerson(person: Person) {
    const vm = this;
    vm.person = person;
    vm.resourceService.getPatients(person.nhsNumber)
      .subscribe(
        (result) => vm.processPatients(result),
        (error) => vm.logger.error(error)
      );
  }

  private processPatients(patients: any) {
    this.patients = [];

    if (!patients)
      return;

    this.patients = patients;

    const services = this.linq.Enumerable().From(patients)
      .Select(p => p.id.serviceId)
      .Distinct()
      .ToArray();

    this.populateServiceMap(services);

    const systems = this.linq.Enumerable().From(patients)
      .Select(p => p.id.systemId)
      .Distinct()
      .ToArray();

    this.populateSystemMap(systems);
  }

  /** SERVICES **/
  private populateServiceMap(serviceIds: string[]) {
    this.serviceMap = {};
    for (const serviceId of serviceIds) {
      const service: Service = new Service();
      service.id = serviceId;
      this.serviceMap[serviceId] = service;
      this.loadServiceName(service);
    }
  }

  private loadServiceName(service: Service) {
    if (service.name != null && service.name !== '')
      return;

    service.name = 'Loading...';

    const vm = this;
    vm.resourceService.getServiceName(service.id)
      .subscribe(
        (result) => vm.processServiceName(service, result),
        (error) => { vm.logger.log(error); service.name = 'Error!'; }
      );
  }

  private processServiceName(service: Service, name: string) {
    service.name = (name == null) ? 'Not Known' : name;

    for (const patient of this.patients) {
      if (patient.id.serviceId === service.id) {
        patient.serviceName = service.name;
        this.updatePatientDisplayName(patient);
      }
    }
  }

  private getServiceName(resource: ServicePatientResource): string {
    const service: Service = this.serviceMap[resource.serviceId];

    if (service)
      return service.name;

    return 'Not Known';
  }

  /** SYSTEMS **/
  private populateSystemMap(systemIds: string[]) {
    this.systemMap = {};
    for (const systemId of systemIds) {
      const system: System = new System();
      system.id = systemId;
      this.systemMap[systemId] = system;
      this.loadSystemName(system);
    }
  }

  private loadSystemName(system: System) {
    if (system.name != null && system.name !== '')
      return;

    system.name = 'Loading...';

    const vm = this;
    vm.resourceService.getSystemName(system.id)
      .subscribe(
        (result) => vm.processSystemName(system, result),
        (error) => { vm.logger.log(error); system.name = 'Error!'; }
      );
  }

  private processSystemName(system: System, name: string) {
    system.name = (name == null) ? 'Not Known' : name;

    for (const patient of this.patients) {
      if (patient.id.systemId === system.id) {
        patient.systemName = system.name;
        this.updatePatientDisplayName(patient)
      }
    }
  }

  private updatePatientDisplayName(patient: Patient) {
    patient.name = patient.patientName + ' @ ' + patient.serviceName + ' (' + patient.systemName + ')';
  }

  private getSystemName(systemId: string): string {
    const system: System = this.systemMap[systemId];

    if (system)
      return system.name;

    return 'Not Known';
  }

  /** RESOURCE DATA LOAD **/
  private loadResources(): void {
    if (this.patientFilter == null || this.patientFilter.length === 0) {
      this.logger.error('Select at least one patient');
      return;
    }

    if (this.resourceFilter == null || this.resourceFilter.length === 0) {
      this.logger.error('Select at least one Resource type');
      return;
    }

    const applicablePatients: ResourceId[] = this.linq.Enumerable().From(this.patients)
      .Where(p => this.patientFilter.indexOf(p.id) > -1)
      .Select(p => p.id)
      .ToArray();

    if (applicablePatients.length === 0)
      return;

    const vm = this;
    vm.patientResourceList = null;
    vm.resourceService.getResources(applicablePatients, ['Patient'])
      .subscribe(
        (result) => vm.patientResourceList = result,
        (error) => vm.logger.error(error)
      );

    vm.clinicalResourceList = null;
    vm.resourceService.getResources(applicablePatients, this.resourceFilter)
      .subscribe(
        (result) => vm.clinicalResourceList = vm.linq.Enumerable().From(result)
          .OrderByDescending(spr => vm.getRecordedDate(spr))
          .ThenByDescending(spr => vm.getDate(spr))
          .ToArray(),
        (error) => vm.logger.error(error)
      );
  }

  /** RECORDED DATE FUNCTIONS **/
  private getRecordedDate(resource: ServicePatientResource): Date {
    if (resource.recordedDate)
      return resource.recordedDate;

    switch (resource.resourceJson.resourceType) {
      case 'Condition': return resource.recordedDate = DateHelper.parse(resource.resourceJson.dateRecorded);
      case 'AllergyIntolerance': return resource.recordedDate = DateHelper.parse(resource.resourceJson.recordedDate);
      case 'DiagnosticOrder': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'DiagnosticReport': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'ProcedureRequest': return resource.recordedDate = DateHelper.parse(resource.resourceJson.orderedOn);
      case 'Encounter': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'EpisodeOfCare': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);       // Period.start!?
      case 'FamilyMemberHistory': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'Immunisation': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'MedicationOrder': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'MedicationStatement': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'Medication': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'Observation': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'Procedure': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'ReferralRequest': return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      case 'Specimen':  return resource.recordedDate = this.getRecordedDateExtension(resource.resourceJson);
      default: return resource.recordedDate = DateHelper.NOT_KNOWN;
    }
  }

  private getRecordedDateExtension(resource: any): Date {
    const RECORDED_DATE = 'http://endeavourhealth.org/fhir/StructureDefinition/primarycare-recorded-date-extension';
    const extensions = this.linq.Enumerable().From(resource.extension)
      .Where(e => e.url === RECORDED_DATE)
      .ToArray();

    if (extensions.length === 0)
      return null;

    return extensions[0].valueDateTime;
  }

  /** EFFECTIVE DATE FUNCTIONS **/
  private getDate(resource: ServicePatientResource): Date {
    if (resource.effectiveDate)
      return resource.effectiveDate;

    switch (resource.resourceJson.resourceType) {
      case 'Patient': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.birthDate);
      case 'AllergyIntolerance': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.onset);
      case 'Condition': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.onsetDateTime);
      case 'DiagnosticOrder': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.event.dateTime);
      case 'DiagnosticReport': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.effectiveDateTime);
      case 'ProcedureRequest': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.scheduledDateTime);
      case 'Encounter': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.period.start);
      case 'EpisodeOfCare': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.period.start);
      case 'FamilyMemberHistory': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.date);
      case 'Immunisation': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.date);
      case 'MedicationOrder': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.dateWritten);
      case 'MedicationStatement': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.dateAsserted);
      case 'Medication': return null;
      case 'Observation': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.effectiveDateTime);
      case 'Procedure': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.performedDateTime);
      case 'ReferralRequest': return resource.effectiveDate = DateHelper.parse(resource.resourceJson.date);
      case 'Specimen':  return resource.effectiveDate = DateHelper.parse(resource.resourceJson.collection.collectedDateTime);
      default: return resource.effectiveDate = DateHelper.NOT_KNOWN;
    }
  }

  /** DESCRIPTION FUNCTIONS **/
  private getDescription(resource: ServicePatientResource): string {
    if (resource.description)
      return resource.description;

    return resource.description = this.getCodeTerm(resource.resourceJson);
  }

  private getCodeTerm(resource: any): string {
    const code = this.getCode(resource);
    if (code == null)
      return null;

    if (code.text != null)
      return code.text;

    if (code.coding)
      return code.coding[0].display;

    return null;
  }

  private getCode(resource: any): any {
    switch (resource.resourceType) {
      case 'AllergyIntolerance': return resource.substance;
      case 'Condition': return resource.code;
      case 'DiagnosticOrder': return (resource.item && resource.item.length > 0) ? resource.item[0].code : null;
      case 'DiagnosticReport': return resource.code;
      case 'ProcedureRequest': return resource.code;
      case 'Encounter': return (resource.reason && resource.reason.length > 0) ? resource.reason[0] : null;
      case 'EpisodeOfCare': return resource.type;
      case 'FamilyMemberHistory': return (resource.condition && resource.condition.length > 0) ? resource.condition[0].code : null;
      case 'Immunisation': return resource.vaccineCode;
      case 'MedicationOrder': return (resource.medicationCodeableConcept) ? resource.medicationCodeableConcept : null;
      case 'MedicationStatement': return (resource.medicationCodeableConcept) ? resource.medicationCodeableConcept : null;
      case 'Medication': return resource.code;
      case 'Observation': return  resource.code;
      case 'Procedure': return resource.code;
      case 'ReferralRequest':
        return (resource.serviceRequested && resource.serviceRequested.length > 0) ? resource.serviceRequested[0] : null;
      case 'Specimen':  return resource.type;
      default: return null;
    }
  }

  /** BASIC LOOKUPS **/
  private getPatientName(resource: ServicePatientResource): string {
    if (resource.resourceJson.name && resource.resourceJson.name.length > 0)
      return resource.resourceJson.name[0].text;

    return 'Not known';
  }

  /** MAP LOOKUPS **/
  private getResourceName(resource: ServicePatientResource): string {
    return this.resourceMap[resource.resourceJson.resourceType];
  }

  private setHighlight(resource: ServicePatientResource) {
/*    if (this.lastHighlight
      && this.lastHighlight.patientId === resource.patientId
      && this.lastHighlight.systemId === resource.systemId
      && this.lastHighlight.serviceId === resource.serviceId)
      return;

    this.lastHighlight = resource;

    for (const patient of this.patientResourceList) {
      if (patient.patientId !== resource.patientId)
        continue;
      if (patient.systemId !== resource.systemId)
        continue;
      if (patient.serviceId !== resource.serviceId)
        continue;

      // this.highlight = patient;
      console.log('Matched!');
      return;
    }

    // this.highlight = null;*/
  }

  private viewResource(resource: ServicePatientResource) {
    ViewerComponent.open(this.modal, this.getResourceName(resource), resource, null, 'Close');
  }
}
