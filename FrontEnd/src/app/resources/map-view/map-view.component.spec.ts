import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MapViewComponent } from './map-view.component';
import {MockResourcesService} from '../../mocks/mock.resources.service';
import {ResourcesService} from '../resources.service';
import {ServicePatientResource} from '../../models/Resource';
import {ControlsModule} from 'eds-angular4/dist/controls';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LoggerModule} from 'eds-angular4';
import {ToastModule} from 'ng2-toastr/ng2-toastr';

describe('MapViewComponent', () => {
  let component: MapViewComponent;
  let fixture: ComponentFixture<MapViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ControlsModule, LoggerModule, NgbModule.forRoot(), ToastModule.forRoot()],
      declarations: [ MapViewComponent ],
      providers: [
        {provide : ResourcesService, useClass: MockResourcesService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MapViewComponent);
    component = fixture.componentInstance;
    component = fixture.componentInstance;
    component.resource = new ServicePatientResource();
    component.resource.resourceJson = { resourceType: 'Patient' };
    component.fieldMappings = [];
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
