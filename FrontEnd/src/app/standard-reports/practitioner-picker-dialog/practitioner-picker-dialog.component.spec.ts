import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PractitionerPickerDialogComponent } from './practitioner-picker-dialog.component';
import {NgbActiveModal, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {MockNgbActiveModal} from '../../mocks/mock.ngb-active-modal';
import {StandardReportsService} from '../standard-reports.service';
import {MockStandardReportsService} from '../../mocks/mock.standard-reports.service';

describe('PractitionerPickerDialogComponent', () => {
  let component: PractitionerPickerDialogComponent;
  let fixture: ComponentFixture<PractitionerPickerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, NgbModule.forRoot()],
      declarations: [ PractitionerPickerDialogComponent ],
      providers: [
        {provide: NgbActiveModal, useClass: MockNgbActiveModal },
        {provide: StandardReportsService, useClass: MockStandardReportsService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PractitionerPickerDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
