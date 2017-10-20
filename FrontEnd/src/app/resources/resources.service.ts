import { Injectable } from '@angular/core';
import {URLSearchParams, Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Patient} from '../models/Patient';
import {ResourceType} from '../models/ResourceType';
import {ServicePatientResource} from '../models/Resource';
import {ResourceId} from '../models/ResourceId';

@Injectable()
export class ResourcesService {

  constructor(private http: Http) { }

  public getConfig(): Observable<any> {
    return this.http.get('api/config', {withCredentials: true})
      .map((response) => response.json());
  }

  public getServiceName(serviceId: string): Observable<string> {
    const params: URLSearchParams = new URLSearchParams();
    params.append('serviceId', serviceId);

    return this.http.get('api/admin/service/name', {search: params, withCredentials: true})
      .map((response) => response.text());
  }

  public getSystemName(systemId: string): Observable<string> {
    const params: URLSearchParams = new URLSearchParams();
    params.append('systemId', systemId);

    return this.http.get('api/admin/system/name', {search: params, withCredentials: true})
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

  public getResources(patients: ResourceId[], resourceTypes: string[]): Observable<ServicePatientResource[]> {
    const resourceRequest = {
      patients: patients,
      resourceTypes: resourceTypes
    };

    return this.http.post('api/resource', resourceRequest)
      .map((response) => response.json());
  }
}
