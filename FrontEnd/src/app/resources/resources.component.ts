import { Component, OnInit } from '@angular/core';
import {ResourcesService} from './resources.service';
import {PersonFindDialogComponent} from '../person-find/person-find-dialog/person-find-dialog.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Service} from '../models/Service';
import {Person} from '../models/Person';
import { LinqService } from 'ng2-linq';
import {ResourceType} from '../models/ResourceType';
import {Patient} from '../models/Patient';
import {LoggerService, MessageBoxDialog} from 'eds-angular4';
import {ViewerComponent} from './viewer/viewer.component';

@Component({
  selector: 'app-resources-component',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  private person: Person;
  private resourceTypes: ResourceType[] = [];
  private resourceMap: any;
  private resourceList: any = [];
  private serviceFilter: string[];
  private resourceFilter: string[];

  constructor(protected logger: LoggerService,
              protected linq: LinqService,
              protected modal: NgbModal,
              protected resourceService: ResourcesService) {
    this.getResourceTypes();
  }

  ngOnInit() {
  }

  private findPerson() {
    PersonFindDialogComponent.open(this.modal)
      .result.then(
      (result) => this.loadPerson(result),
      (error) => this.logger.error(error)
    );
  }

  private loadPerson(person: Person) {
    const vm = this;
    vm.person = person;
    vm.resourceService.getPatients(person.nhsNumber)
      .subscribe(
        (result) => person.patients = result,
        (error) => vm.logger.error(error)
      );
  }

  private getServices(): Service[] {
    if (!this.person || !this.person.patients)
      return [];

    const distinctServices: any = this.linq.Enumerable().From(this.person.patients)
      .Select(p => p.service)
      .Distinct();

    distinctServices.ForEach(s => this.getServiceName(s));

    return distinctServices.ToArray();
  }

  private getServiceName(service: Service) {
    if (service.name != null && service.name !== '')
      return;

    service.name = 'Loading...';

    const vm = this;
    vm.resourceService.getServiceName(service.id)
      .subscribe(
        (result) => service.name = (result == null) ? 'Not Known' : result,
        (error) => { vm.logger.log(error); service.name = 'Error!'; }
      );
  }

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

  private refresh(): void {
    if (this.serviceFilter == null || this.serviceFilter.length === 0) {
      this.logger.error('Select at least one Service');
      return;
    }

    if (this.resourceFilter == null || this.resourceFilter.length === 0) {
      this.logger.error('Select at least one Resource type');
      return;
    }

    const applicablePatients: Patient[] = this.linq.Enumerable().From(this.person.patients)
      .Where(p => this.serviceFilter.indexOf(p.service.id) > -1)
      .ToArray();

    if (applicablePatients.length === 0)
      return;

    const patient: Patient = applicablePatients[0];

    const vm = this;
    vm.resourceList = null;
    vm.resourceService.getPatientResources(patient.id, patient.service.id, this.resourceFilter)
      .subscribe(
        (result) => {
          vm.resourceList = vm.linq.Enumerable().From(result)
            .OrderByDescending(resource => vm.getDate(resource))
            .ToArray();
        },
        (error) => vm.logger.error(error)
      );
  }

  private getDate(resource: any): Date {
    switch (resource.resourceType) {
      case 'AllergyIntolerance': return new Date(resource.onset);
      case 'Condition': return new Date(resource.onsetDateTime);
      case 'DiagnosticOrder': return this.getRecordedDateExtension(resource);
      case 'DiagnosticReport': return new Date(resource.effectiveDateTime);
      case 'ProcedureRequest': return new Date(resource.scheduledDateTime);
      case 'Encounter': return new Date(resource.period.start);
      case 'EpisodeOfCare': return new Date(resource.period.start);
      case 'FamilyMemberHistory': return new Date(resource.date);
      case 'Immunisation': return new Date(resource.date);
      case 'MedicationOrder': return new Date(resource.dateWritten);
      case 'MedicationStatement': return new Date(resource.dateAsserted);
      case 'Medication': return null;
      case 'Observation': return new Date(resource.effectiveDateTime);
      case 'Procedure': return new Date(resource.performedDateTime);
      case 'ReferralRequest': return this.getRecordedDateExtension(resource);
      case 'Specimen':  return this.getRecordedDateExtension(resource);
      default: return null;
    }
  }

  private getRecordedDateExtension(resource: any): Date {
    const RECORDED_DATE = 'http://endeavourhealth.org/fhir/StructureDefinition/primarycare-recorded-date-extension';
    const extension = this.linq.Enumerable().From(resource.extension)
      .SingleOrDefault(null, e => e.url === RECORDED_DATE);

    return (extension === null) ? null : new Date(extension.valueDateTime);
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

  private getResourceName(resourceType: string): string {
    return this.resourceMap[resourceType];
  }

  private viewResource(resource: any) {
    ViewerComponent.open(this.modal, this.getResourceName(resource.resourceType), resource, null, 'Close');
  }
}
