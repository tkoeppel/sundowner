import { TestBed } from '@angular/core/testing';

import { SpotViewServiceService } from './spot-view-service.service';

describe('SpotViewServiceService', () => {
  let service: SpotViewServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpotViewServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
