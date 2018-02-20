import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourcesComponent } from './resources.component';
import {MockSecurityService} from '../mocks/mock.security.service';
import {LoggerModule, SecurityService} from 'eds-angular4';
import {ResourcesService} from './resources.service';
import {MockResourcesService} from '../mocks/mock.resources.service';
import {FormsModule} from '@angular/forms';
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ControlsModule} from 'eds-angular4/dist/controls';

describe('ResourcesComponent', () => {
  let component: ResourcesComponent;
  let fixture: ComponentFixture<ResourcesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, ControlsModule, LoggerModule, ToastModule.forRoot(), NgbModule.forRoot()],
      declarations: [ ResourcesComponent ],
      providers: [
        {provide : SecurityService, useClass: MockSecurityService },
        {provide : ResourcesService, useClass: MockResourcesService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourcesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
