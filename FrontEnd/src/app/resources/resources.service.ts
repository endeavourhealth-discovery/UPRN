import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Patient} from '../models/Patient';
import {ResourceType} from '../models/ResourceType';

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

    return this.http.get('/api/person/patients', {search: params, withCredentials: true})
      .map((response) => response.json());
  }

  public getResourceTypes(): Observable<ResourceType[]> {

    return this.http.get('/api/resource/type', {withCredentials: true})
      .map((response) => response.json());
  }

  public getPatientResources(patientId: string, serviceId: string, resourceTypes: string[]): Observable<any> {
    const params: URLSearchParams = new URLSearchParams();
    params.append('patientId', patientId);
    params.append('serviceId', serviceId);

    for (const resourceType of resourceTypes)
      params.append('resourceTypes', resourceType);

    return this.http.get('/api/resource/patient', {search: params, withCredentials: true})
      .map((response) => response.json());
  }

}
