import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {UPRNPatientResource} from '../models/UPRNPatientResource';
import {Patient} from "../models/Patient";
import {log} from "util";

@Injectable()
export class UPRNResourcesService {

  constructor(private http: Http) { }

    public getPatientsAddr1(filterstr: string): Observable<UPRNPatientResource[]> {
    const params = new URLSearchParams();
    params.append('filter', filterstr);

    //console.trace("Params is: "+params.toString());

    return this.http.get('api/uprnpatientresource/getpatientsaddr1', {search: params})
      .map((response) => response.json());
  }

  public getPatientsPostcode(filterstr: string): Observable<UPRNPatientResource[]> {
    const params = new URLSearchParams();
    params.append('filter', filterstr);

    //console.trace("Params is: "+params.toString());

    return this.http.get('api/uprnpatientresource/getpatientspostcode', {search: params})
      .map((response) => response.json());
  }
}
