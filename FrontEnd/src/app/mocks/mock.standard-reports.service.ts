import { Injectable } from '@angular/core';
import {AbstractMockObservable} from './mock.observable';
import {Observable} from 'rxjs/Observable';
import {Concept} from 'eds-angular4/dist/coding/models/Concept';
import {Practitioner} from '../models/Practitioner';
import {URLSearchParams} from '@angular/http';
import {ReportLibraryItem} from '../models/ReportLibraryItem';

@Injectable()
export class MockStandardReportsService extends AbstractMockObservable {
  runReport(reportUuid: string, reportParams: Map<string, string>) {
    this._fakeContent = [];
    return this;
  }

  exportNHSNumbers(uuid: string) {
    this._fakeContent = '';
    return this;
  }

  exportData(uuid: string) {
    this._fakeContent = '';
    return this;
  }

  getEncounterTypeCodes() {
    this._fakeContent = [];
    return this;
  }

  getReferralTypes() {
    this._fakeContent = [];
    return this;
  }

  getReferralPriorities() {
    this._fakeContent = [];
    return this;
  }

  getServiceName(uuid: string) {
    this._fakeContent = '';
    return this;
  }

  findPractitioner(searchData: string, organisationUuid: string) {
    this._fakeContent = [];
    return this;
  }
}
