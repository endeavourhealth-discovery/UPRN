import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportParamsDialogComponent } from './report-params-dialog.component';
import {FormsModule} from '@angular/forms';
import {CuiDatePickerComponent} from 'eds-angular4/dist/cuicontrols/cuidate-picker/cuidate-picker.component';
import {NgbActiveModal, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LoggerModule, SecurityService} from 'eds-angular4';
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {MockNgbActiveModal} from '../../mocks/mock.ngb-active-modal';
import {CodingService} from 'eds-angular4/dist/coding';
import {MockCodingService} from '../../mocks/mock.coding.service';
import {MockSecurityService} from '../../mocks/mock.security.service';
import {StandardReportsService} from '../standard-reports.service';
import {MockStandardReportsService} from '../../mocks/mock.standard-reports.service';

describe('ReportParamsDialogComponent', () => {
  let component: ReportParamsDialogComponent;
  let fixture: ComponentFixture<ReportParamsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, NgbModule.forRoot(), LoggerModule, ToastModule.forRoot()],
      declarations: [ CuiDatePickerComponent, ReportParamsDialogComponent ],
      providers: [
        {provide: NgbActiveModal, useClass: MockNgbActiveModal },
        {provide: CodingService, useClass: MockCodingService },
        {provide: SecurityService, useClass: MockSecurityService },
        {provide: StandardReportsService, useClass: MockStandardReportsService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportParamsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
