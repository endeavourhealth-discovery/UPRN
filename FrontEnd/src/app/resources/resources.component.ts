import { Component, OnInit } from '@angular/core';
import {ResourcesService} from "./resources.service";
import {PersonFindDialogComponent} from "../person-find/person-find-dialog/person-find-dialog.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Service} from "../models/Service";
import {Person} from "../models/Person";
import { LinqService } from 'ng2-linq';

@Component({
  selector: 'resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  private person : Person;
  private serviceFilter : Service;
  private resourceFilter : string;

  constructor(private linq: LinqService, protected modal : NgbModal, protected service : ResourcesService) {
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

  private loadPerson(person : Person) {
    let vm = this;
    vm.person = person;
    vm.service.getPatients(person.nhsNumber)
      .subscribe(
        (result) => person.patients = result,
        (error) => console.error(error)
      );
  }

  private getServices() : Service[] {
    if (!this.person || !this.person.patients)
      return [];

    return this.linq.Enumerable().From(this.person.patients)
      .Select(p => p.service)
      .Distinct()
      .ToArray();
  }

  private getResources() : String[] {
    return [
      'Episode',
      'Encounter',
      'Condition',
    ];
  }

  private refresh() : void {
    // fetch data from db
  }
}
