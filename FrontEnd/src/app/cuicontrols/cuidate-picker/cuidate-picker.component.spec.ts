import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CuiDatePickerComponent } from './cuidate-picker.component';

describe('CuidatePickerComponent', () => {
  let component: CuiDatePickerComponent;
  let fixture: ComponentFixture<CuiDatePickerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CuiDatePickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CuiDatePickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
