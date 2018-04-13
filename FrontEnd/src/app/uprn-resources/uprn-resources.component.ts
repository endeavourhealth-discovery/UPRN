///<reference path="../../../node_modules/@angular/core/src/metadata/directives.d.ts"/>
import {Component, OnInit} from '@angular/core';
import {UPRNResourcesService} from './uprn-resources.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Service} from '../models/Service';
import {LoggerService} from 'eds-angular4';
import {DateHelper} from '../helpers/date.helper';
import {UPRNPatientResource} from '../models/UPRNPatientResource';
import {debuglog} from "util";

@Component({
  selector: 'app-uprn-resources-component',
  templateUrl: './uprn-resources.component.html',
  styleUrls: ['./uprn-resources.component.css']
})
export class UPRNResourcesComponent implements OnInit {

  private resourceMap: any;
  private serviceMap: any;

  protected patientResourceList: UPRNPatientResource[] = [];
  protected patientResourceFilter: string[];

  protected selectedPatientResType: string;

  protected resourceTypes: string;

  protected filterAddr1: string;
  protected filterPostcode: string;

  public patientResource: UPRNPatientResource;

  protected highlight: UPRNPatientResource;
  protected lastHighlight: UPRNPatientResource;

  constructor(protected logger: LoggerService,
              protected modal: NgbModal,
              protected resourceService: UPRNResourcesService) {


  }

  ngOnInit() {

    // Auto-select all patients
    this.selectedPatientResType = "0";

  }

  protected clearPatientResType() {
    console.error("clear list");
  }

  private invalidSelection(): boolean {
    return this.patientResourceFilter == null || this.patientResourceFilter.length === 0;
  }

  /** UPRN Patient Resources Calls **/
  private getPatientResourcesAddr1(): void {
    const vm = this;


    // console.error("Patients Json call");

    vm.resourceService.getPatientsAddr1(this.filterAddr1)
      .subscribe(
        (result) => vm.processPatientResourcesAddr1(result),
        (error) => vm.logger.error(error)
      );

  }

  private getPatientResourcesPostcode(): void {
    const vm = this;


    // console.error("Patients Json call");

    vm.resourceService.getPatientsPostcode(this.filterPostcode)
      .subscribe(
        (result) => vm.processPatientResourcesPostcode(result),
        (error) => vm.logger.error(error)
      );

  }

  private processPatientResourcesAddr1(patientResourceTypes: UPRNPatientResource[]) {
    this.patientResourceList = patientResourceTypes;
    this.resourceMap = {};

    // Placeholder for further processing of patient resource list here once retrieved

  }


  private processPatientResourcesPostcode(patientResourceTypes: UPRNPatientResource[]) {
    this.patientResourceList = patientResourceTypes;
    this.resourceMap = {};

    // Placeholder for further processing of patient resource list here once retrieved

  }

  /** Do all resource initialisation here **/

  /** Do all resource initialisation here **/
  private loadResources(): void {

    // Do initial field validation here
    //if (this.patientFilter == null || this.patientFilter.length === 0) {
    //  this.logger.error('Select at least one patient');
    //  return;
    //}

    // Initialise any variables
    //this.patientResourceList = null;

    // Any initial service calls on loading
    //const vm = this;
    //vm.resourceService.getResources(this.patientFilter, ['Patient'])
    // .subscribe(
    //    (result) => vm.patientResourceList = result,
    //    (error) => vm.logger.error(error)
    //  );

  }

}
