import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewerComponent } from './viewer.component';
import {RawViewComponent} from '../raw-view/raw-view.component';
import {NgbActiveModal, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {MockNgbActiveModal} from '../../mocks/mock.ngb-active-modal';
import {ServicePatientResource} from '../../models/Resource';
import {TemplateViewComponent} from '../template-view/template-view.component';
import {ResourcesService} from '../resources.service';
import {MockResourcesService} from '../../mocks/mock.resources.service';
import {MapViewComponent} from '../map-view/map-view.component';
import {MapDetailComponent} from '../map-detail/map-detail.component';
import {ControlsModule} from 'eds-angular4/dist/controls';

describe('ViewerComponent', () => {
  let component: ViewerComponent;
  let fixture: ComponentFixture<ViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ControlsModule, NgbModule.forRoot()],
      declarations: [ ViewerComponent, RawViewComponent, TemplateViewComponent, MapViewComponent, MapDetailComponent ],
      providers: [
        {provide : NgbActiveModal, useClass: MockNgbActiveModal },
        {provide : ResourcesService, useClass: MockResourcesService }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewerComponent);
    component = fixture.componentInstance;
    component.resource = { resourceJson: {} } as ServicePatientResource;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
