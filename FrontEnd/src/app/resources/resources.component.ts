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

  constructor(private linq: LinqService, protected modal: NgbModal, protected service: ResourcesService) {
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
    vm.service.getPatients(person.nhsNumber)
      .subscribe(
        (result) => person.patients = result,
        (error) => console.error(error)
      );
  }

  private getServices(): Service[] {
    if (!this.person || !this.person.patients)
      return [];

    return this.linq.Enumerable().From(this.person.patients)
      .Select(p => p.service)
      .Distinct()
      .ToArray();
  }

  private getResourceTypes(): void {
    const vm = this;
    vm.service.getResourceTypes()
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
    vm.service.getPatientResources(patient.id, patient.service.id, this.resourceFilter)
      .subscribe(
        (result) => {
          vm.resourceList = result;
          console.log(result);
        },
        (error) => console.error(error)
      );
  }
}
