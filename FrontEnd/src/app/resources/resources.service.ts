import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Patient} from '../models/Patient';
import {ResourceType} from '../models/ResourceType';
import {ServicePatientResource} from '../models/Resource';

@Injectable()
export class ResourcesService {

  constructor(private http: Http) { }

  public getConfig(): Observable<any> {
    return this.http.get('api/config', {withCredentials: true})
      .map((response) => response.json());
  }

  public getServiceName(serviceId): Observable<string> {
    const params: URLSearchParams = new URLSearchParams();
    params.append('serviceId', serviceId);

    return this.http.get('api/admin/service/name', {search: params, withCredentials: true})
      .map((response) => response.text());
  }

  public getPatients(personId: string): Observable<Patient[]> {
    const params: URLSearchParams = new URLSearchParams();
    params.append('personId', personId);

    return this.http.get('api/person/patients', {search: params, withCredentials: true})
      .map((response) => response.json());
  }

  public getResourceTypes(): Observable<ResourceType[]> {

    return this.http.get('api/resource/type', {withCredentials: true})
      .map((response) => response.json());
  }

  public getResources(servicePatientIds: string[], resourceTypes: string[]): Observable<ServicePatientResource[]> {
    const params: URLSearchParams = new URLSearchParams();

    for(const servicePatientId of servicePatientIds)
      params.append('servicePatientId', servicePatientId);

    for(const resourceType of resourceTypes)
      params.append('resourceType', resourceType);

    return this.http.get('api/resource', {search: params, withCredentials: true})
      .map((response) => response.json());
  }
}
