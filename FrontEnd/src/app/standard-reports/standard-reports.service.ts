import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {ReportLibraryItem} from '../models/ReportLibraryItem';
import {URLSearchParams, Http} from '@angular/http';
import {Concept} from 'eds-angular4/dist/coding/models/Concept';
import {Practitioner} from '../models/Practitioner';

@Injectable()
export class StandardReportsService {

  constructor(private http: Http) {
  }

  runReport(reportUuid: string, reportParams: Map<string, string>): Observable<ReportLibraryItem> {
    const params = new URLSearchParams();
    params.set('reportUuid', reportUuid);

    return this.http.post('api/report/runReport', reportParams, {search: params})
      .map((result) => result.json());
  }

  exportNHSNumbers(uuid: string): Observable<string> {
    const params = new URLSearchParams();
    params.set('uuid', uuid);
    return this.http.get('api/report/exportNHS', {search: params})
      .map((result) => result.text());
  }

  exportData(uuid: string): Observable<string> {
    const params = new URLSearchParams();
    params.set('uuid', uuid);
    return this.http.get('api/report/exportData', {search: params})
      .map((result) => result.text());
  }

  getEncounterTypeCodes(): Observable<Concept[]> {
    return this.http.get('api/report/encounterType')
      .map((result) => result.json());
  }

  getReferralTypes(): Observable<Concept[]> {
    return this.http.get('api/report/referralTypes')
      .map((result) => result.json());
  }

  getReferralPriorities(): Observable<Concept[]> {
    return this.http.get('api/report/referralPriorities')
      .map((result) => result.json());
  }

  getServiceName(uuid: string): Observable<string> {
    const params = new URLSearchParams();
    params.set('serviceId', uuid);
    return this.http.get('api/admin/service/name', {search: params})
      .map((result) => result.text());
  }

  findPractitioner(searchData: string, organisationUuid: string): Observable<Practitioner[]> {
    const params = new URLSearchParams();
    params.append('searchData', searchData);
    params.append('organisationUuid', organisationUuid);

    return this.http.get('api/report/searchPractitioner', {search: params})
      .map((result) => result.json());
  }
}
