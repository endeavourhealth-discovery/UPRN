import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StandardReportsComponent } from './standard-reports.component';
import {LibraryModule, LibraryService} from 'eds-angular4/dist/library';
import {FolderModule} from 'eds-angular4/dist/folder';
import {NgbActiveModal, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';
import {LoggerModule, SecurityService} from 'eds-angular4';
import {ToastModule} from 'ng2-toastr/ng2-toastr';
import {MockSecurityService} from '../mocks/mock.security.service';
import {StandardReportsService} from './standard-reports.service';
import {MockStandardReportsService} from '../mocks/mock.standard-reports.service';
import {MockLibraryService} from '../mocks/mock.library.service';
import {ModuleStateService} from 'eds-angular4/dist/common';
import {FolderService} from 'eds-angular4/dist/folder/folder.service';
import {MockFolderService} from '../mocks/mock.folder.service';

describe('StandardReportsComponent', () => {
  let component: StandardReportsComponent;
  let fixture: ComponentFixture<StandardReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [FormsModule, LibraryModule, FolderModule, NgbModule.forRoot(), LoggerModule, ToastModule.forRoot()],
      declarations: [ StandardReportsComponent ],
      providers: [
        ModuleStateService,
        {provide: SecurityService, useClass: MockSecurityService },
        {provide: StandardReportsService, useClass: MockStandardReportsService },
        {provide: LibraryService, useClass: MockLibraryService },
        {provide: FolderService, useClass: MockFolderService }
      ]

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StandardReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
