import { TestBed, inject } from '@angular/core/testing';

import { PersonFindService } from './person-find.service';

describe('PersonFindService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PersonFindService]
    });
  });

  it('should be created', inject([PersonFindService], (service: PersonFindService) => {
    expect(service).toBeTruthy();
  }));
});
