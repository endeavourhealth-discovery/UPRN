import { Component, OnInit } from '@angular/core';
import {PersonFindService} from '../person-find.service';
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Person} from '../../models/Person';
import {LoggerService} from 'eds-angular4';

@Component({
  selector: 'app-person-find-dialog',
  templateUrl: './person-find-dialog.component.html',
  styleUrls: ['./person-find-dialog.component.css']
})
export class PersonFindDialogComponent implements OnInit {

  public searchTerms: string;
  public matches: Person[] = [];
  public selectedPerson: Person = null;

  public static open(modalService: NgbModal) {
    const modalRef = modalService.open(PersonFindDialogComponent, { backdrop: 'static', size: 'lg'});
    return modalRef;
  }

  constructor(protected logger: LoggerService, public activeModal: NgbActiveModal, private service: PersonFindService) { }

  ngOnInit() {
  }

  public search() {
    const vm = this;
    vm.matches = null;
    vm.service.findPerson(vm.searchTerms)
      .subscribe(
        (result) => vm.matches = result,
        (error) => vm.logger.error(error)
      );
  }

  public selectPerson(person: Person, close: boolean) {
    this.selectedPerson = person;
    if (close)
      this.ok();
  }

  public ok() {
    this.activeModal.close(this.selectedPerson);
    this.logger.log('OK Pressed');
  }

  public cancel() {
    this.activeModal.dismiss('cancel');
    this.logger.log('Cancel Pressed');
  }
}
