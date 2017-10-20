import { TestBed, inject } from '@angular/core/testing';

import { PersonFindService } from './person-find.service';
import {HttpModule} from '@angular/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

describe('PersonFindService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule, NgbModule.forRoot()],
      providers: [PersonFindService]
    });
  });

  it('should be created', inject([PersonFindService], (service: PersonFindService) => {
    expect(service).toBeTruthy();
  }));
});
