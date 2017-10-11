import { Injectable } from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Patient} from '../models/Patient';

@Injectable()
export class ResourcesService {

  constructor(private http: Http) { }

  public getConfig(): Observable<any> {
    return this.http.get('/api/config', {withCredentials: true})
      .map((response) => response.json());
  }

  public getPatients(personId: string): Observable<Patient[]> {
    const params: URLSearchParams = new URLSearchParams();
    params.append('personId', personId);

    return this.http.get('/api/person/patients', {params: params, withCredentials: true})
      .map((response) => response.json());
  }
}
