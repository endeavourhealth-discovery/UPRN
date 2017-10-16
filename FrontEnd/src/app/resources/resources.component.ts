import { Component, OnInit } from '@angular/core';
import {ResourcesService} from './resources.service';
import {PersonFindDialogComponent} from '../person-find/person-find-dialog/person-find-dialog.component';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Service} from '../models/Service';
import {Person} from '../models/Person';
import { LinqService } from 'ng2-linq';
import {ResourceType} from '../models/ResourceType';
import {Patient} from '../models/Patient';

@Component({
  selector: 'app-resources-component',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  private person: Person;
  private resourceTypes: ResourceType[] = [];
  private resourceList: any = [];
  private serviceFilter: string[];
  private resourceFilter: string[];

  constructor(protected linq: LinqService, protected modal: NgbModal, protected resourceService: ResourcesService) {
    this.getResourceTypes();
  }

  ngOnInit() {
  }

  private findPerson() {
    PersonFindDialogComponent.open(this.modal)
      .result.then(
      (result) => this.loadPerson(result),
      (error) => console.error(error)
    );
  }

  private loadPerson(person: Person) {
    const vm = this;
    vm.person = person;
    vm.resourceService.getPatients(person.nhsNumber)
      .subscribe(
        (result) => person.patients = result,
        (error) => console.error(error)
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
        (error) => { console.log(error); service.name = 'Error!'; }
      );
  }

  private getResourceTypes(): void {
    const vm = this;
    vm.resourceService.getResourceTypes()
      .subscribe(
        (result) => vm.resourceTypes = result,
        (error) => console.error(error)
      );
  }

  private refresh(): void {
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
        (error) => console.error(error)
      );
  }

  private getDate(resource: any): Date {
    switch (resource.resourceType) {
      case 'Encounter':
      case 'EpisodeOfCare':
        return new Date(resource.period.start);
      case 'Observation':
        return new Date(resource.effectiveDateTime);
      default:
        return null;
    }
  }

  private getCode(resource: any): any {
    let codeableConcept: any = null;
    switch (resource.resourceType) {
      case 'Encounter':
        codeableConcept = (resource.reason && resource.reason.length > 0) ? resource.reason[0] : null;
        break;
      case 'EpisodeOfCare':
        codeableConcept = resource.type;
        break;
      case 'Observation':
        codeableConcept = resource.code;
        break;
    }
  }
}
