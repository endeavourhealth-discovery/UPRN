import { TestBed, inject } from '@angular/core/testing';

import { ResourcesService } from './resources.service';
import { HttpModule} from '@angular/http';

describe('ResourcesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      providers: [ResourcesService]
    });
  });

  it('should be created', inject([ResourcesService], (service: ResourcesService) => {
    expect(service).toBeTruthy();
  }));
});
