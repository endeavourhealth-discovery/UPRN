import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportParamsDialogComponent } from './report-params-dialog.component';

describe('ReportParamsDialogComponent', () => {
  let component: ReportParamsDialogComponent;
  let fixture: ComponentFixture<ReportParamsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportParamsDialogComponent ]
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
