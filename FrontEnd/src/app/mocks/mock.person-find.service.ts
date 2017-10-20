import { Injectable } from '@angular/core';
import {AbstractMockObservable} from './mock.observable';

@Injectable()
export class MockPersonFindService extends AbstractMockObservable {

  getResourceTypes() {
    this._fakeContent = ['Patient', 'Condition'];

    return this;
  }
}
