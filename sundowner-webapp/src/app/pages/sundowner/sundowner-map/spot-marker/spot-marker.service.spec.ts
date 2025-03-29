import { TestBed } from '@angular/core/testing';

import { SpotMarkerService } from './spot-marker.service';

describe('Test SpotMarkerService', () => {
  let service: SpotMarkerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpotMarkerService],
    });
    service = TestBed.inject(SpotMarkerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
