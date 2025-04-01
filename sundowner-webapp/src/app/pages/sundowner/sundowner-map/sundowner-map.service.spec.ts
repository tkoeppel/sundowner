import { TestBed } from '@angular/core/testing';
import { SpotMarkerService } from './spot-marker/spot-marker.service';
import { MapSpotTO } from '../../../../../gensrc';
import { environment } from '../../../../environments/environment';
import { SundownerMapService } from './sundowner-map.service';
import * as L from 'leaflet';

// Mock the 'leaflet' module
jest.mock('leaflet');

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
  const SPOT3 = {
    id: 3,
    location: { lat: 3, lng: 3 },
    avgRating: 4,
    name: 'Spot 3',
  };
  const MAP_ID = 'mapId';

  let service: SundownerMapService;
  let spotMarkerServiceMock: any;
  let mockMap: any;
  let mockTileLayer: any;
  let mockMarkerLayer: any;
  let mockMarker: any;
  let mockDivIcon: any;

  let mapContainer: HTMLDivElement;

  beforeEach(() => {
    // Reset the mocks before each test
    jest.clearAllMocks();

    // Create the mocks inside beforeEach
    mockMap = {
      setView: jest.fn().mockReturnThis(),
      getBounds: jest.fn().mockReturnValue({
        getNorth: jest.fn().mockReturnValue(NORTH),
        getSouth: jest.fn().mockReturnValue(SOUTH),
        getWest: jest.fn().mockReturnValue(WEST),
        getEast: jest.fn().mockReturnValue(EAST),
        getNorthEast: jest.fn().mockReturnValue({ lat: NORTH, lng: EAST }),
        getSouthWest: jest.fn().mockReturnValue({ lat: SOUTH, lng: WEST }),
      }),
      on: jest.fn().mockReturnThis(),
      addLayer: jest.fn().mockReturnThis(),
      removeLayer: jest.fn().mockReturnThis(),
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

    // Set up the mocked L object
    (L.map as jest.Mock).mockReturnValue(mockMap);
    (L.tileLayer as unknown as jest.Mock).mockReturnValue(mockTileLayer);
    (L.layerGroup as jest.Mock).mockReturnValue(mockMarkerLayer);
    (L.marker as jest.Mock).mockReturnValue(mockMarker);
    (L.divIcon as jest.Mock).mockReturnValue(mockDivIcon);
    (L.DivIcon as unknown as jest.Mock).mockReturnValue(mockDivIcon);

    spotMarkerServiceMock = {
      getSpotMarkerHTML: jest
        .fn()
        .mockReturnValue(document.createElement('div')),
    };

    TestBed.configureTestingModule({
      providers: [
        SundownerMapService,
        { provide: SpotMarkerService, useValue: spotMarkerServiceMock },
      ],
    });

    service = TestBed.inject(SundownerMapService);

    mapContainer = document.createElement('div');
    mapContainer.id = MAP_ID;
    document.body.appendChild(mapContainer);
  });

  afterEach(() => {
    document.body.removeChild(mapContainer);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize the map', () => {
    const onMapMove = jest.fn();
    service.initMap(MAP_ID, [51.505, -0.09], onMapMove);

    expect(L.map).toHaveBeenCalledWith(MAP_ID, {
      center: [51.505, -0.09],
      zoom: 16,
    });
    expect(L.tileLayer).toHaveBeenCalledWith(
      `${environment.tileProviderLink}?access-token=${environment.accessToken}`,
      {}
    );
    expect(mockTileLayer.addTo).toHaveBeenCalledWith(mockMap);
    expect(mockMap.attributionControl.addAttribution).toHaveBeenCalledWith(
      SundownerMapService['LEAFLET_ATTRIBUTION']
    );
    expect(mockMap.on).toHaveBeenCalledWith('moveend', expect.any(Function));
    expect(mockMap.on).toHaveBeenCalledWith('click', expect.any(Function));
    expect(onMapMove).toHaveBeenCalledWith(mockMap.getBounds());
  });

  it('should mark spots', () => {
    const spots: MapSpotTO[] = [SPOT1, SPOT2];
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    service.markSpots(spots);

    expect(L.marker).toHaveBeenCalledTimes(2);
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
    // Simulate the click event on the marker
    const clickHandler = (mockMarker.on as jest.Mock).mock.calls[0][1];
    clickHandler();
    expect(mockMap.setView).toHaveBeenCalled();
    expect(mockMarker.getElement().classList.add).toHaveBeenCalledWith(
      'selected-spot'
    );
  });

  it('should close spot preview', () => {
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    service.markSpots([SPOT1]);
    // Simulate the click event on the marker
    const markerClickHandler = (mockMarker.on as jest.Mock).mock.calls[0][1];
    markerClickHandler();
    // Simulate the click event on the map
    const mapClickHandler = (mockMap.on as jest.Mock).mock.calls[1][1];
    mapClickHandler();
    expect(mockMarker.getElement().classList.remove).toHaveBeenCalledWith(
      'selected-spot'
    );
  });

  it('should add new spots and keep existing ones', () => {
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    service.markSpots([SPOT1, SPOT2]);
    expect(L.marker).toHaveBeenCalledTimes(2);
    expect(mockMarkerLayer.addTo).toHaveBeenCalledTimes(1);
    jest.clearAllMocks();

    (L.marker as jest.Mock).mockReturnValue(mockMarker);
    service.markSpots([SPOT1, SPOT2, SPOT3]);
    expect(L.marker).toHaveBeenCalledTimes(1);
    expect(mockMarkerLayer.removeLayer).toHaveBeenCalledTimes(0);
    expect(mockMarkerLayer.addTo).toHaveBeenCalledTimes(1);
  });

  it('should not remove the selected spot', () => {
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    service.markSpots([SPOT1, SPOT2]);
    const markerClickHandler = (mockMarker.on as jest.Mock).mock.calls[0][1];
    markerClickHandler();
    jest.clearAllMocks();
    service.markSpots([SPOT2]);
    expect(mockMarkerLayer.removeLayer).toHaveBeenCalledTimes(0);
    expect(L.marker).toHaveBeenCalledTimes(0);
  });

  it('should not do anything if map or markerLayer is not initialized', () => {
    service.markSpots([SPOT1]);
    expect(L.marker).not.toHaveBeenCalled();
  });

  it('should call onMapMove when moveend event is triggered', () => {
    const onMapMove = jest.fn();
    service.initMap(MAP_ID, [51.505, -0.09], onMapMove);
    const moveendHandler = (mockMap.on as jest.Mock).mock.calls[0][1];
    moveendHandler();
    expect(onMapMove).toHaveBeenCalledTimes(2);
    expect(onMapMove).toHaveBeenLastCalledWith(mockMap.getBounds());
  });

  it('should create a DivIcon with the correct HTML', () => {
    service.initMap(MAP_ID, [51.505, -0.09], jest.fn());
    (L.DivIcon as jest.Mock).mockClear();
    (L.marker as jest.Mock).mockClear();
    (L.marker as jest.Mock).mockReturnValue(mockMarker);

    service.markSpots([SPOT1]);

    expect(spotMarkerServiceMock.getSpotMarkerHTML).toHaveBeenCalledWith(SPOT1);
    expect(L.DivIcon).toHaveBeenCalled();
    expect(L.DivIcon).toHaveBeenCalledWith(
      expect.objectContaining({
        className: 'spot-marker',
        html: expect.anything(),
      })
    );
    expect(L.marker).toHaveBeenCalled();
  });
});
