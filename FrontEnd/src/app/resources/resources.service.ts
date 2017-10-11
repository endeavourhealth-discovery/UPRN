import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ResourcesService {

  constructor(private http : Http) { }

  public getConfig() : Observable<any> {
    return this.http.get('/api/config', {withCredentials : true})
      .map((response) => response.json());
  }

}
