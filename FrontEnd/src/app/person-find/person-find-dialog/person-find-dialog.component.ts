import { Component, OnInit } from '@angular/core';
import {PersonFindService} from "../person-find.service";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Person} from "../../models/Person";

@Component({
  selector: 'app-person-find-dialog',
  templateUrl: './person-find-dialog.component.html',
  styleUrls: ['./person-find-dialog.component.css']
})
export class PersonFindDialogComponent implements OnInit {

  public static open(modalService: NgbModal) {
    const modalRef = modalService.open(PersonFindDialogComponent, { backdrop : 'static', size : 'lg'});
    return modalRef;
  }

  private searchTerms : string;
  private matches : Person[] = null;
  private selectedPerson : Person = null;


  constructor(public activeModal: NgbActiveModal, private service : PersonFindService) { }

  ngOnInit() {
  }

  private search() {
    let vm = this;
    vm.service.findPerson(vm.searchTerms)
      .subscribe(
        (result) => vm.matches = result,
        (error) => console.error(error)
      );
  }

  private selectPerson(person : Person, close : boolean){
    this.selectedPerson = person;
    if (close)
      this.ok();
  }

  private ok() {
    this.activeModal.close(this.selectedPerson);
    console.log('OK Pressed');
  }

  private cancel() {
    this.activeModal.dismiss('cancel');
    console.log('Cancel Pressed');
  }
}
