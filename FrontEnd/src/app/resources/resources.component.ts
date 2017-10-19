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

@Component({
  selector: 'app-resources-component',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  private person: Person;
  private resourceMap: any;
  private serviceMap: any;

  protected resourceTypes: ResourceType[] = [];
  protected resourceFilter: string[];

  protected patients: Patient[] = [];
  protected patientFilter: string[];

  protected patientResourceList: ServicePatientResource[] = [];
  protected clinicalResourceList: ServicePatientResource[] = [];

  protected highlight: ServicePatientResource;

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
      .Select(p => p.service)
      .Distinct(s => s.id)
      .ToArray();

    this.populateServiceMap(services);
  }

  private populateServiceMap(services: Service[]) {
    this.serviceMap = {};
    for (const service of services) {
      this.serviceMap[service.id] = service;
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
        (result) => vm.processService(service, result),
        (error) => { vm.logger.log(error); service.name = 'Error!'; }
      );
  }

  private processService(service: Service, name: string) {
    service.name = (name == null) ? 'Not Known' : name;
    for (const patient of this.patients) {
      if (patient.service.id === service.id)
        patient.name = patient.name + ' @ ' + name;
    }
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

    const applicablePatientIds: string[] = this.linq.Enumerable().From(this.patients)
      .Where(p => this.patientFilter.indexOf(p.id) > -1)
      .Select(p => p.id)
      .ToArray();

    if (applicablePatientIds.length === 0)
      return;

    const vm = this;
    vm.patientResourceList = null;
    vm.resourceService.getResources(applicablePatientIds, ['Patient'])
      .subscribe(
        (result) => vm.patientResourceList = result,
        (error) => vm.logger.error(error)
      );

    vm.clinicalResourceList = null;
    vm.resourceService.getResources(applicablePatientIds, this.resourceFilter)
      .subscribe(
        (result) => vm.clinicalResourceList = vm.linq.Enumerable().From(result)
          .OrderByDescending(spr => vm.getRecordedDate(spr.resourceJson))
          .ThenByDescending(spr => vm.getDate(spr.resourceJson))
          .ToArray(),
        (error) => vm.logger.error(error)
      );
  }

  /** RECORDED DATE FUNCTIONS **/
  private getRecordedDate(resource: any): Date {
    switch (resource.resourceType) {
      case 'Condition': return DateHelper.parse(resource.dateRecorded);

      case 'AllergyIntolerance': return DateHelper.parse(resource.onset);
      case 'DiagnosticOrder': return this.getRecordedDateExtension(resource);
      case 'DiagnosticReport': return DateHelper.parse(resource.effectiveDateTime);
      case 'ProcedureRequest': return DateHelper.parse(resource.scheduledDateTime);
      case 'Encounter': return DateHelper.parse(resource.period.start);
      case 'EpisodeOfCare': return DateHelper.parse(resource.period.start);
      case 'FamilyMemberHistory': return DateHelper.parse(resource.date);
      case 'Immunisation': return DateHelper.parse(resource.date);
      case 'MedicationOrder': return DateHelper.parse(resource.dateWritten);
      case 'MedicationStatement': return DateHelper.parse(resource.dateAsserted);
      case 'Medication': return null;
      case 'Observation': return DateHelper.parse(resource.effectiveDateTime);
      case 'Procedure': return DateHelper.parse(resource.performedDateTime);
      case 'ReferralRequest': return this.getRecordedDateExtension(resource);
      case 'Specimen':  return this.getRecordedDateExtension(resource);
      default: return DateHelper.NOT_KNOWN;
    }
  }

  private getRecordedDateExtension(resource: any): Date {
    const RECORDED_DATE = 'http://endeavourhealth.org/fhir/StructureDefinition/primarycare-recorded-date-extension';
    const extension = this.linq.Enumerable().From(resource.extension)
      .SingleOrDefault(null, e => e.url === RECORDED_DATE);

    return (extension === null) ? null : DateHelper.parse(extension.valueDateTime);
  }

  /** EFFECTIVE DATE FUNCTIONS **/
  private getDate(resource: any): Date {
    switch (resource.resourceType) {
      case 'Patient': return DateHelper.parse(resource.birthDate);
      case 'AllergyIntolerance': return DateHelper.parse(resource.onset);
      case 'Condition': return DateHelper.parse(resource.onsetDateTime);
      case 'DiagnosticOrder': return this.getRecordedDateExtension(resource);
      case 'DiagnosticReport': return DateHelper.parse(resource.effectiveDateTime);
      case 'ProcedureRequest': return DateHelper.parse(resource.scheduledDateTime);
      case 'Encounter': return DateHelper.parse(resource.period.start);
      case 'EpisodeOfCare': return DateHelper.parse(resource.period.start);
      case 'FamilyMemberHistory': return DateHelper.parse(resource.date);
      case 'Immunisation': return DateHelper.parse(resource.date);
      case 'MedicationOrder': return DateHelper.parse(resource.dateWritten);
      case 'MedicationStatement': return DateHelper.parse(resource.dateAsserted);
      case 'Medication': return null;
      case 'Observation': return DateHelper.parse(resource.effectiveDateTime);
      case 'Procedure': return DateHelper.parse(resource.performedDateTime);
      case 'ReferralRequest': return this.getRecordedDateExtension(resource);
      case 'Specimen':  return this.getRecordedDateExtension(resource);
      default: return DateHelper.NOT_KNOWN;
    }
  }

  /** DESCRIPTION FUNCTIONS **/
  private getDescription(resource: any): string {
    return this.getCodeTerm(resource);
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
  private getPatientName(resource: any): string {
    if (resource.name && resource.name.length > 0)
      return resource.name[0].text;

    return 'Not known';
  }

  /** MAP LOOKUPS **/
  private getServiceName(serviceId: string): string {
    const service: Service = this.serviceMap[serviceId];

    if (service)
      return service.name;

    return 'Not Known';
  }

  private getResourceName(resourceType: string): string {
    return this.resourceMap[resourceType];
  }

  private viewResource(resource: any) {
    ViewerComponent.open(this.modal, this.getResourceName(resource.resourceType), resource, null, 'Close');
  }
}
