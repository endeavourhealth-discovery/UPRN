import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultClinicalViewComponent } from './default-clinical-view.component';

describe('DefaultClinicalViewComponent', () => {
  let component: DefaultClinicalViewComponent;
  let fixture: ComponentFixture<DefaultClinicalViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DefaultClinicalViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DefaultClinicalViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
