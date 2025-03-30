import { TestBed } from '@angular/core/testing';
import { MapService } from './sundowner-map.service';
import { SpotMarkerService } from './spot-marker/spot-marker.service';
import { MapSpotTO } from '../../../../../gensrc';
import { environment } from '../../../../environments/environment';
import {
  LatLngExpression,
  LatLngBounds,
  layerGroup,
  tileLayer,
  marker,
  DivIcon,
  map,
  Map,
  LayerGroup,
  TileLayer,
  Marker,
  LeafletMouseEvent,
} from 'leaflet';

describe('MapService', () => {
  let service: MapService;
  let spotMarkerServiceSpy: jest.Mocked<SpotMarkerService>;
  let mockMap: jest.Mocked<Map>;
  let mockTileLayer: jest.Mocked<TileLayer>;
  let mockMarkerLayer: jest.Mocked<LayerGroup>;
  let mockMarker: jest.Mocked<Marker>;
  let mockDivIcon: jest.Mocked<DivIcon>;

  beforeEach(() => {
    spotMarkerServiceSpy = {
      getSpotMarkerHTML: jest.fn(),
    } as unknown as jest.Mocked<SpotMarkerService>;

    mockMap = {
      setView: jest.fn(),
      getBounds: jest.fn(),
      on: jest.fn(),
      attributionControl: {
        addAttribution: jest.fn(),
      } as any,
      addLayer: jest.fn(),
      removeLayer: jest.fn(),
      hasLayer: jest.fn(),
    } as unknown as jest.Mocked<Map>;

    mockTileLayer = {
      addTo: jest.fn(),
    } as unknown as jest.Mocked<TileLayer>;

    mockMarkerLayer = {
      addTo: jest.fn(),
      removeLayer: jest.fn(),
    } as unknown as jest.Mocked<LayerGroup>;

    mockMarker = {
      addTo: jest.fn(),
      on: jest.fn(),
      getElement: jest.fn(),
    } as unknown as jest.Mocked<Marker>;

    mockDivIcon = {
      // No methods to mock in DivIcon
    } as unknown as jest.Mocked<DivIcon>;

    mockMap.getBounds.mockReturnValue(new LatLngBounds([0, 0], [1, 1]));
    mockMarker.addTo.mockReturnValue(mockMarker);
    mockMarker.on.mockReturnValue(mockMarker);
    mockMarker.getElement.mockReturnValue(document.createElement('div'));

    TestBed.configureTestingModule({
      providers: [
        MapService,
        { provide: SpotMarkerService, useValue: spotMarkerServiceSpy },
      ],
    });
    service = TestBed.inject(MapService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('initMap', () => {
    it('should initialize the map with correct parameters', () => {
      const mapId = 'mapId';
      const currentPos: LatLngExpression = [10, 20];
      const onMapMove = jest.fn();

      service.initMap(mapId, currentPos, onMapMove);

      expect(map).toHaveBeenCalledWith(mapId, {
        center: currentPos,
        zoom: 16,
      });
      expect(tileLayer).toHaveBeenCalledWith(
        `${environment.tileProviderLink}?access-token=${environment.accessToken}`,
        {}
      );
      expect(mockTileLayer.addTo).toHaveBeenCalledWith(mockMap);
      expect(mockMap.attributionControl.addAttribution).toHaveBeenCalled();
      expect(layerGroup).toHaveBeenCalled();
      expect(mockMap.on).toHaveBeenCalledWith('moveend', expect.any(Function));
      expect(mockMap.on).toHaveBeenCalledWith('click', expect.any(Function));
      expect(onMapMove).toHaveBeenCalledWith(mockMap.getBounds());
    });
  });

  describe('markSpots', () => {
    it('should add new spots and remove unseen spots', () => {
      const newSpots: MapSpotTO[] = [
        { id: 1, location: { lat: 1, lng: 1 } },
        { id: 2, location: { lat: 2, lng: 2 } },
      ] as MapSpotTO[];
      const oldSpots: MapSpotTO[] = [
        { id: 2, location: { lat: 2, lng: 2 } },
        { id: 3, location: { lat: 3, lng: 3 } },
      ] as MapSpotTO[];

      service.initMap('mapId', [0, 0], () => {});

      // Add old spots
      mockMarkerLayer.removeLayer.mockClear();
      mockMarker.getElement.mockReturnValue(document.createElement('div'));

      service.markSpots(newSpots);

      expect(mockMarkerLayer.removeLayer).toHaveBeenCalledTimes(1);
      expect(mockMarkerLayer.addTo).toHaveBeenCalledWith(mockMap);
      expect(mockMarker.addTo).toHaveBeenCalledTimes(1);
      expect(mockMarker.on).toHaveBeenCalledTimes(1);
    });

    it('should not remove selected spot', () => {
      const newSpots: MapSpotTO[] = [
        { id: 1, location: { lat: 1, lng: 1 } },
      ] as MapSpotTO[];
      const oldSpots: MapSpotTO[] = [
        { id: 1, location: { lat: 1, lng: 1 } },
        { id: 2, location: { lat: 2, lng: 2 } },
      ] as MapSpotTO[];

      service.initMap('mapId', [0, 0], () => {});

      // Add old spots
      (service as any)['openSpotPreview']({
        data: oldSpots[0],
        marker: mockMarker,
      });
      mockMarkerLayer.removeLayer.mockClear();

      service.markSpots(newSpots);

      expect(mockMarkerLayer.removeLayer).toHaveBeenCalledTimes(1);
    });

    it('should do nothing if map or markerLayer is not initialized', () => {
      const newSpots: MapSpotTO[] = [
        { id: 1, location: { lat: 1, lng: 1 } },
      ] as MapSpotTO[];
      service.markSpots(newSpots);
      expect(mockMarkerLayer.removeLayer).not.toHaveBeenCalled();
    });
  });

  describe('openSpotPreview', () => {
    it('should set the view and add selected-spot class', () => {
      service.initMap('mapId', [0, 0], () => {});
      const spot: MapSpotTO = {
        id: 1,
        location: { lat: 1, lng: 1 },
      } as MapSpotTO;
      const mockElement = document.createElement('div');
      mockMarker.getElement.mockReturnValue(mockElement);

      expect(mockMap.setView).toHaveBeenCalled();
      expect(mockElement.classList.contains('selected-spot')).toBe(true);
    });

    it('should do nothing if map is not initialized', () => {
      const spot: MapSpotTO = {
        id: 1,
        location: { lat: 1, lng: 1 },
      } as MapSpotTO;
      expect(mockMap.setView).not.toHaveBeenCalled();
    });
  });

  describe('closeSpotPreview', () => {
    it('should remove selected-spot class', () => {
      service.initMap('mapId', [0, 0], () => {});
      const spot: MapSpotTO = {
        id: 1,
        location: { lat: 1, lng: 1 },
      } as MapSpotTO;
      const mockElement = document.createElement('div');
      mockElement.classList.add('selected-spot');
      mockMarker.getElement.mockReturnValue(mockElement);

      expect(mockElement.classList.contains('selected-spot')).toBe(false);
    });

    it('should do nothing if no spot is selected', () => {
      service.initMap('mapId', [0, 0], () => {});
      const mockElement = document.createElement('div');
      mockMarker.getElement.mockReturnValue(mockElement);
      expect(mockElement.classList.contains('selected-spot')).toBe(false);
    });
  });

  describe('private methods', () => {
    beforeEach(() => {
      service.initMap('mapId', [0, 0], () => {});
    });

    it('createIcon should call getSpotMarkerHTML', () => {
      const spot: MapSpotTO = {
        id: 1,
        location: { lat: 1, lng: 1 },
      } as MapSpotTO;
      expect(spotMarkerServiceSpy.getSpotMarkerHTML).toHaveBeenCalledWith(spot);
    });

    it('removeSpotMarker should remove the marker from the layer and the currentSpots', () => {
      const spot: MapSpotTO = {
        id: 1,
        location: { lat: 1, lng: 1 },
      } as MapSpotTO;
      expect(mockMarkerLayer.removeLayer).toHaveBeenCalledWith(mockMarker);
      expect(service['currentSpots'].has(spot.id)).toBe(false);
    });

    it('addSpotMarker should add the marker to the layer and the currentSpots', () => {
      const spot: MapSpotTO = {
        id: 1,
        location: { lat: 1, lng: 1 },
      } as MapSpotTO;
      expect(mockMarker.addTo).toHaveBeenCalledWith(mockMarkerLayer);
      expect(mockMarker.on).toHaveBeenCalledWith('click', expect.any(Function));
      expect(service['currentSpots'].has(spot.id)).toBe(true);
    });

    it('removeSpotMarkers should call removeSpotMarker for each spot', () => {
      const spots: MapSpotTO[] = [
        { id: 1, location: { lat: 1, lng: 1 } },
        { id: 2, location: { lat: 2, lng: 2 } },
      ] as MapSpotTO[];
      const removeSpotMarkerSpy = jest.spyOn(
        service as any,
        'removeSpotMarker'
      );
      expect(removeSpotMarkerSpy).toHaveBeenCalledTimes(2);
    });

    it('addSpotMarkers should call addSpotMarker for each spot', () => {
      const spots: MapSpotTO[] = [
        { id: 1, location: { lat: 1, lng: 1 } },
        { id: 2, location: { lat: 2, lng: 2 } },
      ] as MapSpotTO[];
      const addSpotMarkerSpy = jest.spyOn(service as any, 'addSpotMarker');
      expect(addSpotMarkerSpy).toHaveBeenCalledTimes(2);
    });
  });
});
