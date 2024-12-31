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

  public markSpots(spots: MapSpotTO[]) {
    if (!this.map || !this.markerLayer) {
      return;
    }

    // reset all markers
    this.markerLayer.clearLayers();

    // add markers to layers
    for (let spot of spots) {
      marker([spot.location.lat, spot.location.lng], {
        icon: this.createIcon(spot),
      }).addTo(this.markerLayer);
    }

    // add to map
    this.markerLayer.addTo(this.map);
  }

  private createIcon(spot: MapSpotTO) {
    return new DivIcon({
      className: 'spot-marker',
      html: this._spotMarkerService.getSpotMarkerHTML(spot),
    });
  }
}
