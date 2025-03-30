import {
  ComponentFixture,
  fakeAsync,
  TestBed,
  tick,
} from '@angular/core/testing';
import { SundownerComponent } from './sundowner.component';
import { SpotsService, MapSpotTO } from '../../../../gensrc';
import { of, Subject } from 'rxjs';
import { MapBounds } from './sundowner-map/MapBounds';
import { MockService } from 'ng-mocks';

describe('SundownerComponent', () => {
  let component: SundownerComponent;
  let fixture: ComponentFixture<SundownerComponent>;
  let spotsServiceMock: SpotsService;
  let getPointsInViewSubject: Subject<MapSpotTO[]>;
  const mockSpots: MapSpotTO[] = [
    { id: 1, name: 'Spot 1', location: { lat: 1, lng: 1 }, avgRating: 5 },
    { id: 2, name: 'Spot 2', location: { lat: 2, lng: 2 }, avgRating: 4 },
  ];

  beforeEach(async () => {
    getPointsInViewSubject = new Subject<MapSpotTO[]>();
    spotsServiceMock = MockService(SpotsService, {
      getPointsInView: jest.fn().mockReturnValue(getPointsInViewSubject),
    });

    await TestBed.configureTestingModule({
      providers: [{ provide: SpotsService, useValue: spotsServiceMock }],
    }).compileComponents();

    fixture = TestBed.createComponent(SundownerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have default START_POS', () => {
    expect(component.START_POS).toEqual({
      lat: 48.7979389287977,
      lng: 9.800386608684917,
    });
  });

  it('should initialize spots on ngOnInit', fakeAsync(() => {
    component.ngOnInit();
    tick(300);

    expect(spotsServiceMock.getPointsInView).toHaveBeenCalledTimes(1);
    expect(component.spots).toEqual([]);
  }));

  it('should update spots when handleMapMove is called', async () => {
    const newBounds: MapBounds = { minX: 10, minY: 10, maxX: 20, maxY: 20 };
    spotsServiceMock.getPointsInView = jest.fn().mockReturnValue(of(mockSpots));
    getPointsInViewSubject.next(mockSpots);

    component.handleMapMove(newBounds);

    // Wait for debounceTime to pass
    await new Promise((resolve) => setTimeout(resolve, 350));

    expect(spotsServiceMock.getPointsInView).toHaveBeenLastCalledWith(
      newBounds.minX,
      newBounds.minY,
      newBounds.maxX,
      newBounds.maxY,
      component['MAX_POINTS']
    );
    expect(component.spots).toEqual(mockSpots);
  });

  it('should not call getPointsInView immediately when handleMapMove is called', async () => {
    const newBounds: MapBounds = { minX: 10, minY: 10, maxX: 20, maxY: 20 };
    jest.clearAllMocks();
    spotsServiceMock.getPointsInView = jest.fn().mockReturnValue(of(mockSpots));

    component.handleMapMove(newBounds);

    expect(spotsServiceMock.getPointsInView).not.toHaveBeenCalled();
  });
});
