import { Injectable } from '@angular/core';
import {
  LatLngExpression,
  LatLngBounds,
  layerGroup,
  tileLayer,
  marker,
  DivIcon,
  map,
} from 'leaflet';
import { MapSpotTO } from '../../../../gensrc';
import { SpotMarkerService } from './spot-marker.service';

@Injectable({
  providedIn: 'root',
})
export class MapService {
  private map: L.Map | undefined;
  private tiles: L.TileLayer | undefined;
  private markerLayer: L.LayerGroup | undefined;

  private currentSpots: Map<number, MapSpot> = new Map<number, MapSpot>();

  constructor(private _spotMarkerService: SpotMarkerService) {
    // nothing to do
  }

  public createMap(
    mapId: string,
    currentPos: LatLngExpression,
    onMapMove: (bounds: LatLngBounds) => void
  ): L.Map {
    this.map = map(mapId, {
      center: currentPos,
      zoom: 16, // initial zoom
    });

    this.initTileLayer();

    this.markerLayer = layerGroup();

    onMapMove(this.map.getBounds());

    this.map.on('moveend', () => {
      onMapMove(this.map!.getBounds());
    });

    return this.map;
  }

  private initTileLayer() {
    this.tiles = tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 19,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );
    this.tiles.addTo(this.map!);
  }

  public markSpots(newSpots: MapSpotTO[]) {
    if (!this.map || !this.markerLayer) {
      return;
    }

    // remove spots
    const spotsToRemove = [...this.currentSpots.values()]
      .filter(
        (spot) => !newSpots.find((newSpot) => newSpot.id === spot.data.id)
      )
      .map((spot) => spot.data);
    this.removeSpotMarkers(spotsToRemove);

    // add new spots
    const spotsToAdd = newSpots.filter(
      (newSpot) => !this.currentSpots.has(newSpot.id)
    );
    this.addSpotMarkers(spotsToAdd);

    // add to map
    this.markerLayer.addTo(this.map);
  }

  private createIcon(spot: MapSpotTO) {
    return new DivIcon({
      className: 'spot-marker',
      html: this._spotMarkerService.getSpotMarkerHTML(spot),
    });
  }

  private removeSpotMarkers(spotsToRemove: MapSpotTO[]) {
    for (const spot of spotsToRemove) {
      const spotToRemove = this.currentSpots.get(spot.id);
      if (spotToRemove) {
        this.markerLayer?.removeLayer(spotToRemove.marker);
        this.currentSpots.delete(spotToRemove.data.id);
      }
    }
  }

  private addSpotMarkers(spots: MapSpotTO[]) {
    for (const spot of spots) {
      const spotMarker = marker([spot.location.lat, spot.location.lng], {
        icon: this.createIcon(spot),
      });

      const spotsToAdd = {
        data: spot,
        marker: spotMarker,
      };

      spotsToAdd.marker.addTo(this.markerLayer!);
      this.currentSpots.set(spot.id, spotsToAdd);
    }
  }
}

/**  This is a simple interface to hold the data and the marker for a spot. */
interface MapSpot {
  /** The map spot data */
  data: MapSpotTO;
  /** The marker on the map */
  marker: L.Marker;
}
