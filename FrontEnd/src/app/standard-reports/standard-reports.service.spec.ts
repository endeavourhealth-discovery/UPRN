import { TestBed, inject } from '@angular/core/testing';

import { StandardReportsService } from './standard-reports.service';

describe('StandardReportsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StandardReportsService]
    });
  });

  it('should be created', inject([StandardReportsService], (service: StandardReportsService) => {
    expect(service).toBeTruthy();
  }));
});
