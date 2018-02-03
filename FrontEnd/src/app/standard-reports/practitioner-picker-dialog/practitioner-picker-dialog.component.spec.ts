import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PractitionerPickerDialogComponent } from './practitioner-picker-dialog.component';

describe('PractitionerPickerDialogComponent', () => {
  let component: PractitionerPickerDialogComponent;
  let fixture: ComponentFixture<PractitionerPickerDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PractitionerPickerDialogComponent ]
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
