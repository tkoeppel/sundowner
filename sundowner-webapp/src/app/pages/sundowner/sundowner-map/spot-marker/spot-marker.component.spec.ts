import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SpotMarkerComponent } from './spot-marker.component';
import { MapSpotTO } from '../../../../../../gensrc';

describe('SpotMarkerComponent', () => {
  let component: SpotMarkerComponent;
  let fixture: ComponentFixture<SpotMarkerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpotMarkerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SpotMarkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize circumference correctly', () => {
    expect(component.circumference).toBe(2 * Math.PI * 14);
  });

  it('should calculate finalDashoffset correctly when spot is provided', () => {
    const mockSpot: MapSpotTO = {
      id: 1,
      name: 'Test Spot',
      location: { lat: 10, lng: 20 },
      avgRating: 7,
    };
    component.spot = mockSpot;
    component.ngOnInit();
    const expectedDashoffset =
      component.circumference * (1 - mockSpot.avgRating / 10);
    expect(component.finalDashoffset).toBe(expectedDashoffset);
  });

  it('should set finalDashoffset to 0 when spot is not provided', () => {
    component.spot = undefined;
    component.ngOnInit();
    expect(component.finalDashoffset).toBe(0);
  });

  it('should calculate finalDashoffset correctly with different avgRating', () => {
    const mockSpot1: MapSpotTO = {
      id: 1,
      name: 'Test Spot 1',
      location: { lat: 10, lng: 20 },
      avgRating: 5,
    };
    const mockSpot2: MapSpotTO = {
      id: 2,
      name: 'Test Spot 2',
      location: { lat: 30, lng: 40 },
      avgRating: 10,
    };
    const mockSpot3: MapSpotTO = {
      id: 3,
      name: 'Test Spot 3',
      location: { lat: 50, lng: 60 },
      avgRating: 0,
    };

    component.spot = mockSpot1;
    component.ngOnInit();
    expect(component.finalDashoffset).toBeCloseTo(
      component.circumference * (1 - mockSpot1.avgRating / 10)
    );

    component.spot = mockSpot2;
    component.ngOnInit();
    expect(component.finalDashoffset).toBeCloseTo(
      component.circumference * (1 - mockSpot2.avgRating / 10)
    );

    component.spot = mockSpot3;
    component.ngOnInit();
    expect(component.finalDashoffset).toBeCloseTo(
      component.circumference * (1 - mockSpot3.avgRating / 10)
    );
  });
});
