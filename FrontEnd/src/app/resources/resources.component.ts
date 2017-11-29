import {Component, OnInit} from '@angular/core';
import {ResourcesService} from './resources.service';
import {PersonFindDialogComponent} from '../person-find/person-find-dialog/person-find-dialog.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Service} from '../models/Service';
import {Person} from '../models/Person';
import {ResourceType} from '../models/ResourceType';
import {Patient} from '../models/Patient';
import {LoggerService} from 'eds-angular4';
import {ViewerComponent} from './viewer/viewer.component';
import {ServicePatientResource} from '../models/Resource';
import { DateHelper} from '../helpers/date.helper';
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

  public person: Person;
  protected patients: Patient[] = [];
  protected patientFilter: ResourceId[];

  protected patientResourceList: ServicePatientResource[] = [];
  protected clinicalResourceList: ServicePatientResource[] = [];

  protected highlight: ServicePatientResource;
  protected lastHighlight: ServicePatientResource;

  constructor(protected logger: LoggerService,
              protected modal: NgbModal,
              protected resourceService: ResourcesService) {
    this.getResourceTypes();
  }

  ngOnInit() {
  }

  private invalidSelection(): boolean {
    return this.patientFilter == null || this.patientFilter.length == 0 || this.resourceFilter == null || this.resourceFilter.length == 0;
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
  public findPerson() {
    PersonFindDialogComponent.open(this.modal)
      .result.then(
      (result) => this.loadPerson(result),
      (error) => this.logger.error(error)
    );
  }

  /** INITIAL DATA LOAD **/
  private loadPerson(person: Person) {
    const vm = this;
    this.patientResourceList = [];
    this.clinicalResourceList = [];
    this.patients = [];
    this.patientFilter = [];

    vm.person = person;
    if (this.person.nhsNumber && this.person.nhsNumber !== '')
    vm.resourceService.getPatients(person.nhsNumber)
      .subscribe(
        (result) => vm.processPatients(result),
        (error) => vm.logger.error(error)
      );
    else
      vm.resourceService.getPatient(person.patientId.serviceId, person.patientId.systemId, this.person.patientId.patientId)
        .subscribe(
          (result) => vm.processPatients([result]),
          (error) => vm.logger.error(error)
        );

  }

  private processPatients(patients: Patient[]) {
    this.patients = [];

    if (!patients)
      return;

    this.patients = patients;

    this.serviceMap = {};
    this.systemMap = {};

    for (const patient of patients) {
      if (!this.serviceMap[patient.id.serviceId]) {
        const service: Service = new Service();
        service.id = patient.id.serviceId;
        this.serviceMap[service.id] = service;
        this.loadServiceName(service);
      }

      if (!this.systemMap[patient.id.systemId]) {
        const system: System = new System();
        system.id = patient.id.systemId;
        this.systemMap[system.id] = system;
        this.loadSystemName(system);
      }

      this.setPatientTooltip(patient);

      // Auto-select all patients
      this.patientFilter.push(patient.id);
    }
  }

  /** SERVICES **/
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

  private getLocalIds(patient: ServicePatientResource): string {
    if (!patient.resourceJson.identifier || patient.resourceJson.identifier.length === 0)
      return '';

    const localIds: string[] = [];
    for (const localId of patient.resourceJson.identifier) {
      localIds.push(localId.value);
    }

    return localIds.join();
  }

  /** SYSTEMS **/
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

    if (!this.patientFilter || this.patientFilter.length === 0)
      return;

    this.patientResourceList = null;
    this.clinicalResourceList = null;

    const vm = this;
    vm.resourceService.getResources(this.patientFilter, ['Patient'])
      .subscribe(
        (result) => vm.patientResourceList = result,
        (error) => vm.logger.error(error)
      );

    vm.resourceService.getResources(this.patientFilter, this.resourceFilter)
      .subscribe(
        (result) => {
          vm.clinicalResourceList = vm.sortResources(result);
        },
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

    if (!resource || !resource.extension)
      return DateHelper.NOT_KNOWN;

    const extension = resource.extension.find((e) => e.url === RECORDED_DATE);

    if (!extension)
      return DateHelper.NOT_KNOWN;

    return DateHelper.parse(extension.valueDateTime);
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
      case 'Medication': return resource.effectiveDate = DateHelper.NOT_KNOWN;
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
    if (resource.resourceJson.name && resource.resourceJson.name.length > 0) {
      const name = resource.resourceJson.name[0];

      if (name.text && name.text !== '')
        return name.text;

      const surnames = (name.family == null) ? '' : name.family.join(' ') + ', ';
      const forenames = (name.given == null) ? '' : name.given.join(' ');
      const title = (name.title == null) ? '' : '(' + name.title.join(' ') + ')';

      return surnames + forenames + title;
    }

    return 'Not known';
  }

  /** MAP LOOKUPS **/
  private getResourceName(resource: ServicePatientResource): string {
    const resourceName: string = this.resourceMap[resource.resourceJson.resourceType];
    if (resourceName && resourceName !== '')
      return resourceName;

    return resource.resourceJson.resourceType;
  }

  private viewResource(resource: ServicePatientResource) {
    ViewerComponent.open(this.modal, this.getResourceName(resource), resource, null, 'Close');
  }

  private sortResources(array) {
    const len = array.length;
    if (len < 2) {
      return array;
    }
    const pivot = Math.ceil(len / 2);
    return this.mergeResources(this.sortResources(array.slice(0, pivot)), this.sortResources(array.slice(pivot)));
  }

  private mergeResources(left, right) {
    let result = [];
    while ((left.length > 0) && (right.length > 0)) {
      if (this.getRecordedDate(left[0]) > this.getRecordedDate(right[0])) {
        result.push(left.shift());
      } else {
        result.push(right.shift());
      }
    }

    result = result.concat(left, right);
    return result;
  }

  private setPatientTooltip(patient: any) {
    patient.tooltip = 'Local ID(s)';
    patient.tooltipKvp = patient.localIds;
  }
}
