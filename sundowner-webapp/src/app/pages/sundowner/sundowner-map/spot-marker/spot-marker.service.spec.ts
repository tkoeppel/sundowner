import { TestBed } from '@angular/core/testing';

import { SpotMarkerService } from './spot-marker.service';

describe('SpotMarkerService', () => {
  let service: SpotMarkerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpotMarkerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
