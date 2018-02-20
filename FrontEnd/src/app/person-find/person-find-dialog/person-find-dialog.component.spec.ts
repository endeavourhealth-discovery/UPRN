import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonFindDialogComponent } from './person-find-dialog.component';
import {FormsModule} from '@angular/forms';
import {LoggerModule} from 'eds-angular4';
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {NgbActiveModal, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MockNgbActiveModal} from '../../mocks/mock.ngb-active-modal';
import {MockPersonFindService} from '../../mocks/mock.person-find.service';
import {PersonFindService} from '../person-find.service';
import {ControlsModule} from 'eds-angular4/dist/controls';

describe('PatientFindDialogComponent', () => {
  let component: PersonFindDialogComponent;
  let fixture: ComponentFixture<PersonFindDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, ControlsModule, LoggerModule, ToastModule.forRoot(), NgbModule.forRoot()],
      declarations: [ PersonFindDialogComponent ],
      providers: [
        {provide: NgbActiveModal, useClass: MockNgbActiveModal },
        {provide: PersonFindService, useClass: MockPersonFindService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFindDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
