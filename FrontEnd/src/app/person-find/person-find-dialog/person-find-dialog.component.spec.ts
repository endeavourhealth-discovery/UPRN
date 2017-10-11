import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonFindDialogComponent } from './person-find-dialog.component';

describe('PatientFindDialogComponent', () => {
  let component: PersonFindDialogComponent;
  let fixture: ComponentFixture<PersonFindDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonFindDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFindDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
