import {Component, Input, OnInit} from '@angular/core';
import {NgbModal, NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {Practitioner} from '../../models/Practitioner';
import {PractitionerPickerDialogComponent} from '../practitioner-picker-dialog/practitioner-picker-dialog.component';
import {Report} from '../../models/Report';
import {Concept} from 'eds-angular4/dist/coding/models/Concept';
import {CodeSetValue} from 'eds-angular4/dist/coding/models/CodeSetValue';
import {SecurityService} from 'eds-angular4';
import {StandardReportsService} from '../standard-reports.service';
import {Organisation} from '../../models/Organisation';
import {DateHelper} from '../../helpers/date.helper';
import {CodePickerDialog, CodingService} from 'eds-angular4/dist/coding';

@Component({
  selector: 'app-report-params-dialog',
  templateUrl: './report-params-dialog.component.html',
  styleUrls: ['./report-params-dialog.component.css']
})
export class ReportParamsDialogComponent implements OnInit {
  @Input() report: Report;

  organisation: string;
  userOrganisations: Organisation[];
  encounterTypes: Concept[];
  referralTypes: Concept[];
  referralPriorities: Concept[];

  runDate: Date;
  includeDeceased: false;
  effectiveDate: Date;
  regType: number;
  gender: number;
  dobMin: Date;
  dobMax: Date;
  originalCode: string;
  valueMax: number;
  valueMin: number;
  snomedCode: CodeSetValue;
  authType: number;
  practitioner: Practitioner;
  dmdCode: number;
  encounterType: number;
  referralSnomedCode: CodeSetValue;
  referralOriginalCode: string;
  referralType: number;
  referralPriority: number;


  public static open(modalService: NgbModal, report: Report) {
    const modalRef = modalService.open(ReportParamsDialogComponent, {backdrop: 'static', size: 'lg'});
    modalRef.componentInstance.report = report;
    return modalRef;
  }

  constructor(protected modalService: NgbModal,
              protected activeModal: NgbActiveModal,
              protected codingService: CodingService,
              protected securityService: SecurityService,
              protected standardReportsService: StandardReportsService) {
    this.loadEncounterTypes();
    this.loadReferralTypes();
    this.loadReferralPriorities();
    this.loadUserOrganisations();
  }

  ngOnInit(): void {
    // work out prompts from query text
    this.runDate = new Date();

    if (!this.report)
      return;

    // Check query for remaining prompts
    if (this.report.query.indexOf(':EffectiveDate') >= 0) this.effectiveDate = null;
    if (this.report.query.indexOf(':SnomedCode') >= 0) this.snomedCode = null;
    if (this.report.query.indexOf(':RegistrationType') >= 0) this.regType = null;
    if (this.report.query.indexOf(':Gender') >= 0) this.gender = null;
    if (this.report.query.indexOf(':DobMin') >= 0) this.dobMin = null;
    if (this.report.query.indexOf(':DobMax') >= 0) this.dobMax = null;
    if (this.report.query.indexOf(':OriginalCode') >= 0) this.originalCode = null;
    if (this.report.query.indexOf(':ValueMin') >= 0) this.valueMin = null;
    if (this.report.query.indexOf(':ValueMax') >= 0) this.valueMax = null;
    if (this.report.query.indexOf(':AuthType') >= 0) this.authType = null;
    if (this.report.query.indexOf(':Practitioner') >= 0) this.practitioner = null;
    // DM&D
    if (this.report.query.indexOf(':EncounterType') >= 0) this.encounterType = null;
    if (this.report.query.indexOf(':ReferralSnomedCode') >= 0) this.referralSnomedCode = null;
    if (this.report.query.indexOf(':ReferralOriginalCode') >= 0) this.referralOriginalCode = null;
    if (this.report.query.indexOf(':ReferralType') >= 0) this.referralType = null;
    if (this.report.query.indexOf(':ReferralPriority') >= 0 ) this.referralPriority = null;
  }

  getDeceasedText() {
    return this.includeDeceased ? 'Include' : 'Exclude';
  }

  loadEncounterTypes() {
    const vm = this;
    vm.standardReportsService.getEncounterTypeCodes()
      .subscribe(
        (result) => vm.encounterTypes = result
      );
  }

  loadReferralTypes() {
    const vm = this;
    vm.standardReportsService.getReferralTypes()
      .subscribe(
        (result) => vm.referralTypes = result
      );
  }

  loadReferralPriorities() {
    const vm = this;
    vm.standardReportsService.getReferralPriorities()
      .subscribe(
        (result) => vm.referralPriorities = result
      );
  }

  selectSnomed() {
    const vm = this;
    CodePickerDialog.open(vm.modalService, [], true)
      .result.then(
      (result) => {
        if (result) {
          vm.snomedCode = result[0];
          vm.snomedCode.term = 'Loading...';
          vm.codingService.getPreferredTerm(vm.snomedCode.code)
            .subscribe(
              (term) => vm.snomedCode.term = term.preferredTerm
            );
        }
      }
    )
  }

  clearSnomed() {
    this.snomedCode = null;
  }

  selectReferralSnomed() {
    const vm = this;
    CodePickerDialog.open(vm.modalService, [], true, {code: '3457005', term: 'Patient referral'} as CodeSetValue)
      .result.then(
      (result) => {
        if (result) {
          vm.referralSnomedCode = result[0];
          vm.referralSnomedCode.term = 'Loading...';
          vm.codingService.getPreferredTerm(vm.referralSnomedCode.code)
            .subscribe(
              (term) => vm.referralSnomedCode.term = term.preferredTerm
            );
        }
      }
    )
  }

  clearReferralSnomed() {
    this.referralSnomedCode = null;
  }


  selectPractitioner() {
    const vm = this;
    PractitionerPickerDialogComponent.open(vm.modalService, this.organisation)
      .result.then(
      (result) => vm.practitioner = result
    );
  }

  clearPractitioner() {
    this.practitioner = null;
  }

  setEffectiveDate($event) {
    if ($event)
      this.effectiveDate = $event;
  }

  setDobMin($event) {
    if ($event)
      this.dobMin = $event;
  }

  setDobMax($event) {
    if ($event)
      this.dobMax = $event;
  }

  hide(item: any) {
    return item === undefined;
  }

  loadUserOrganisations() {
    const vm = this;
    if (!vm.userOrganisations) {

      vm.userOrganisations = [];
      for (const orgGroup of vm.securityService.getCurrentUser().organisationGroups) {
        const orgRole = new Organisation(orgGroup.organisationId, 'Loading...');
        vm.standardReportsService.getServiceName(orgRole.id)
          .subscribe(
            (result) => {
              if (result != null && result !== '') {
                orgRole.name = result;
                vm.userOrganisations.push(orgRole);
              }
            },
            (error) => {
              console.log(error);
              orgRole.name = 'Unknown';
              vm.userOrganisations.push(orgRole);
            }
          );
      }
    }
    return vm.userOrganisations;
  }

  ok() {
    const params: any = {};

    params.RunDate = '\'' + DateHelper.toSqlDateString(this.runDate) + '\'';
    params.OrganisationUuid = this.organisation;
    params.DateOfDeath = (this.includeDeceased) ? 'null' : '\'' + DateHelper.toSqlDateString(this.runDate) + '\'';
    params.EffectiveDate = (this.effectiveDate) ? '\'' + DateHelper.toSqlDateString(this.effectiveDate) + '\'' : 'null';
    params.RegistrationType = (this.regType) ? this.regType : 'null';
    params.Gender = (this.gender) ? this.gender : 'null';
    params.DobMin = (this.dobMin) ? '\'' + DateHelper.toSqlDateString(this.dobMin) + '\'' : 'null';
    params.DobMax = (this.dobMax) ? '\'' + DateHelper.toSqlDateString(this.dobMax) + '\'' : 'null';
    params.SnomedCode = (this.snomedCode) ? this.snomedCode.code : 'null';
    params.OriginalCode = (this.originalCode) ? '\'' + this.originalCode + '\'' : 'null';
    params.ValueMin = (this.valueMin) ? this.valueMin : 'null';
    params.ValueMax = (this.valueMax) ? this.valueMax  : 'null';
    params.AuthType = (this.authType) ? this.authType : 'null';
    params.Practitioner = (this.practitioner) ? this.practitioner.id : 'null';
    // DM & D
    params.EncounterType = (this.encounterType) ? this.encounterType : 'null';
    params.ReferralSnomedCode = (this.referralSnomedCode) ? this.referralSnomedCode.code : 'null';
    params.ReferralOriginalCode = (this.referralOriginalCode) ? '\'' + this.referralOriginalCode + '\'' : 'null';
    params.ReferralType = (this.referralType) ? this.referralType : 'null';
    params.ReferralPriority = (this.referralPriority) ? this.referralPriority : 'null';

    this.activeModal.close(params);
    console.log('OK Pressed');
  }

  cancel() {
    this.activeModal.close(null);
    console.log('Cancel Pressed');
  }
}
