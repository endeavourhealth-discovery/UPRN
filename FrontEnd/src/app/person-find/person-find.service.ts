import { Injectable } from '@angular/core';
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {Person} from "../models/Person";

@Injectable()
export class PersonFindService {

  constructor(private http : Http) { }

  public findPerson(searchTerms : string) : Observable<Person[]> {
    let params : URLSearchParams = new URLSearchParams();
    params.append('searchTerms', searchTerms);

    return this.http.get('/api/person', {params : params, withCredentials : true})
      .map((response) => response.json());
  }
}
