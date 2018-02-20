import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RawViewComponent } from './raw-view.component';
import {MockResourcesService} from '../../mocks/mock.resources.service';
import {ResourcesService} from '../resources.service';
import {ServicePatientResource} from '../../models/Resource';

describe('RawViewComponent', () => {
  let component: RawViewComponent;
  let fixture: ComponentFixture<RawViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RawViewComponent ],
      providers: [
        {provide : ResourcesService, useClass: MockResourcesService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RawViewComponent);
    component = fixture.componentInstance;
    component = fixture.componentInstance;
    component.resource = new ServicePatientResource();
    component.resource.resourceJson = { resourceType: 'Patient' };
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
