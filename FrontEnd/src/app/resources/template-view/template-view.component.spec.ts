import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateViewComponent } from './template-view.component';
import {ResourcesService} from '../resources.service';
import {MockResourcesService} from '../../mocks/mock.resources.service';
import {ServicePatientResource} from '../../models/Resource';

describe('TemplateViewComponent', () => {
  let component: TemplateViewComponent;
  let fixture: ComponentFixture<TemplateViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplateViewComponent ],
      providers: [
        {provide : ResourcesService, useClass: MockResourcesService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateViewComponent);
    component = fixture.componentInstance;
    component.resource = new ServicePatientResource();
    component.resource.resourceJson = { resourceType: 'Patient' };
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
