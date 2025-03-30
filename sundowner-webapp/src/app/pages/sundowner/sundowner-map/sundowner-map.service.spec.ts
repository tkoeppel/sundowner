import { TestBed } from '@angular/core/testing';
import { SpotMarkerService } from './spot-marker/spot-marker.service';
import { MapSpotTO } from '../../../../../gensrc';
import { environment } from '../../../../environments/environment';
import { MapService } from './sundowner-map.service';

describe('MapService', () => {
  const NORTH = 52;
  const SOUTH = 50;
  const WEST = -1;
  const EAST = 1;
  const SPOT1 = {
    id: 1,
    location: { lat: 1, lng: 1 },
    avgRating: 5,
    name: 'Spot 1',
  };
  const SPOT2 = {
    id: 2,
    location: { lat: 2, lng: 2 },
    avgRating: 3,
    name: 'Spot 2',
  };
  const MAP_ID = 'mapId';

  let service: MapService;
  let spotMarkerServiceMock: any;
  let originalL: any;
  let mockMap: any;
  let mockTileLayer: any;
  let mockMarkerLayer: any;
  let mockMarker: any;
  let mockDivIcon: any;

  let mapContainer: HTMLDivElement;

  beforeEach(() => {
    spotMarkerServiceMock = {
      getSpotMarkerHTML: jest
        .fn()
        .mockReturnValue(document.createElement('div')),
    };

    originalL = (global as any).L;
    mockMap = {
      setView: jest.fn().mockReturnThis(),
      getBounds: jest.fn().mockReturnValue({
        getNorth: jest.fn().mockReturnValue(NORTH),
        getSouth: jest.fn().mockReturnValue(SOUTH),
        getWest: jest.fn().mockReturnValue(WEST),
        getEast: jest.fn().mockReturnValue(EAST),
      }),
      on: jest.fn().mockReturnThis(),
      addLayer: jest.fn().mockReturnThis(),
      attributionControl: {
        addAttribution: jest.fn(),
      },
    };
    mockTileLayer = {
      addTo: jest.fn().mockReturnThis(),
    };
    mockMarkerLayer = {
      addTo: jest.fn().mockReturnThis(),
      removeLayer: jest.fn(),
    };
    mockMarker = {
      addTo: jest.fn().mockReturnThis(),
      on: jest.fn().mockReturnThis(),
      getElement: jest.fn().mockReturnValue({
        classList: {
          add: jest.fn(),
          remove: jest.fn(),
        },
      }),
    };
    mockDivIcon = {};

    (global as any).L = {
      map: jest.fn().mockReturnValue(mockMap),
      tileLayer: jest.fn().mockReturnValue(mockTileLayer),
      layerGroup: jest.fn().mockReturnValue(mockMarkerLayer),
      marker: jest.fn().mockReturnValue(mockMarker),
      divIcon: jest.fn().mockReturnValue(mockDivIcon),
      DivIcon: jest.fn().mockReturnValue(mockDivIcon),
    };

    TestBed.configureTestingModule({
      providers: [
        MapService,
        { provide: SpotMarkerService, useValue: spotMarkerServiceMock },
      ],
    });

    service = TestBed.inject(MapService);

    mapContainer = document.createElement('div');
    mapContainer.id = MAP_ID;
    document.body.appendChild(mapContainer);
  });

  afterEach(() => {
    (global as any).L = originalL;
    document.body.removeChild(mapContainer);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize the map', () => {
    const onMapMove = jest.fn();
    service.initMap(MAP_ID, [51.505, -0.09], onMapMove);

    expect((global as any).L.map).toHaveBeenCalledWith(MAP_ID, {
      center: [51.505, -0.09],
      zoom: 16,
    });
    expect((global as any).L.tileLayer).toHaveBeenCalledWith(
      `${environment.tileProviderLink}?access-token=${environment.accessToken}`,
      {}
    );
    expect(mockTileLayer.addTo).toHaveBeenCalledWith(mockMap);
    expect(mockMap.attributionControl.addAttribution).toHaveBeenCalledWith(
      MapService['LEAFLET_ATTRIBUTION']
    );
    expect(mockMap.on).toHaveBeenCalled();
    expect(onMapMove).toHaveBeenCalled();
  });

  it('should mark spots', () => {
    const spots: MapSpotTO[] = [SPOT1, SPOT2];
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    service.markSpots(spots);

    expect((global as any).L.marker).toHaveBeenCalledTimes(2);
    expect(mockMarkerLayer.addTo).toHaveBeenCalledWith(mockMap);
  });

  it('should remove unseen spots', () => {
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    service.markSpots([SPOT1]);
    service.markSpots([]);

    expect(mockMarkerLayer.removeLayer).toHaveBeenCalled();
  });

  it('should open spot preview', () => {
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    service.markSpots([SPOT1]);
    (mockMarker.on as jest.Mock).mock.calls[0][1]();
    expect(mockMap.setView).toHaveBeenCalled();
  });

  it('should close spot preview', () => {
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    service.markSpots([SPOT1]);
    (mockMarker.on as jest.Mock).mock.calls[0][1]();
    (mockMap.on as jest.Mock).mock.calls[1][1]();
    expect(mockMarker.getElement().classList.remove).toHaveBeenCalled();
  });
});
