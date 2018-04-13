import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {UPRNResourcesComponent } from './uprn-resources.component';
import {MockSecurityService} from '../mocks/mock.security.service';
import {LoggerModule, SecurityService} from 'eds-angular4';
import {UPRNResourcesService} from './uprn-resources.service';
import {MockResourcesService} from '../mocks/mock.resources.service';
import {FormsModule} from '@angular/forms';
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ControlsModule} from 'eds-angular4/dist/controls';

describe('UPRNResourcesComponent', () => {
  let component: UPRNResourcesComponent;
  let fixture: ComponentFixture<UPRNResourcesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, ControlsModule, LoggerModule, ToastModule.forRoot(), NgbModule.forRoot()],
      declarations: [ UPRNResourcesComponent ],
      providers: [
        {provide : SecurityService, useClass: MockSecurityService },
        {provide : UPRNResourcesService, useClass: MockResourcesService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UPRNResourcesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
