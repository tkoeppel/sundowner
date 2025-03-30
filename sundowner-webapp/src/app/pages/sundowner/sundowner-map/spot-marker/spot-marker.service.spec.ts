import { TestBed } from '@angular/core/testing';
import { SpotMarkerService } from './spot-marker.service';
import { SpotMarkerComponent } from './spot-marker.component';
import { MapSpotTO } from '../../../../../../gensrc';
import { ComponentFactoryService } from '../../../../core/service/component-factory.service';
import { ComponentRef } from '@angular/core';

describe('SpotMarkerService', () => {
  let service: SpotMarkerService;
  let componentFactoryServiceSpy: jest.Mocked<ComponentFactoryService>;
  let mockComponentRef: jest.Mocked<ComponentRef<SpotMarkerComponent>>;
  let mockElement: HTMLDivElement;

  beforeEach(() => {
    mockElement = document.createElement('div');
    mockComponentRef = {
      instance: {
        spot: undefined,
      },
      hostView: {},
    } as unknown as jest.Mocked<ComponentRef<SpotMarkerComponent>>;

    componentFactoryServiceSpy = {
      createComponent: jest.fn().mockReturnValue(mockComponentRef),
    } as unknown as jest.Mocked<ComponentFactoryService>;

    TestBed.configureTestingModule({
      providers: [
        SpotMarkerService,
        {
          provide: ComponentFactoryService,
          useValue: componentFactoryServiceSpy,
        },
      ],
    });
    service = TestBed.inject(SpotMarkerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getSpotMarkerHTML', () => {
    it('should create a div element', () => {
      const mockSpot: MapSpotTO = {
        id: 1,
        name: 'Test Spot',
        location: { lat: 10, lng: 20 },
        avgRating: 7,
      } as MapSpotTO;

      jest.spyOn(document, 'createElement').mockReturnValue(mockElement);

      service.getSpotMarkerHTML(mockSpot);

      expect(document.createElement).toHaveBeenCalledWith('div');
    });

    it('should call createComponent with correct parameters', () => {
      const mockSpot: MapSpotTO = {
        id: 1,
        name: 'Test Spot',
        location: { lat: 10, lng: 20 },
        avgRating: 7,
      } as MapSpotTO;

      jest.spyOn(document, 'createElement').mockReturnValue(mockElement);

      service.getSpotMarkerHTML(mockSpot);

      expect(componentFactoryServiceSpy.createComponent).toHaveBeenCalledWith(
        SpotMarkerComponent,
        mockElement
      );
    });

    it('should set the spot on the component instance', () => {
      const mockSpot: MapSpotTO = {
        id: 1,
        name: 'Test Spot',
        location: { lat: 10, lng: 20 },
        avgRating: 7,
      } as MapSpotTO;

      jest.spyOn(document, 'createElement').mockReturnValue(mockElement);

      service.getSpotMarkerHTML(mockSpot);

      expect(mockComponentRef.instance.spot).toEqual(mockSpot);
    });

    it('should return the created element', () => {
      const mockSpot: MapSpotTO = {
        id: 1,
        name: 'Test Spot',
        location: { lat: 10, lng: 20 },
        avgRating: 7,
      } as MapSpotTO;

      jest.spyOn(document, 'createElement').mockReturnValue(mockElement);

      const result = service.getSpotMarkerHTML(mockSpot);

      expect(result).toBe(mockElement);
    });
  });
});
