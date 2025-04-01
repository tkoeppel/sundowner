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
import { MapSpotTO } from '../../../../../gensrc';
import { SpotMarkerService } from './spot-marker/spot-marker.service';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SundownerMapService {
  private static readonly LEAFLET_ATTRIBUTION =
    '<a href="https://www.jawg.io?utm_medium=map&utm_source=attribution" target="_blank">&copy; Jawg</a> - <a href="https://www.openstreetmap.org?utm_medium=map-attribution&utm_source=jawg" target="_blank">&copy; OSM</a>&nbsp;contributors';

  private map: L.Map | undefined;
  private tiles: L.TileLayer | undefined;
  private markerLayer: L.LayerGroup | undefined;

  private currentSpots: Map<number, MapSpot> = new Map<number, MapSpot>();
  private selectedSpot: MapSpot | undefined;

  constructor(private _spotMarkerService: SpotMarkerService) {
    // nothing to do
  }

  public initMap(
    mapId: string,
    currentPos: LatLngExpression,
    onMapMove: (bounds: LatLngBounds) => void
  ): void {
    this.map = map(mapId, {
      center: currentPos,
      zoom: 16, // initial zoom
    });

    this.initTileLayer(this.tiles, this.map);
    this.initMapEvents(this.map, onMapMove);
    this.markerLayer = layerGroup();

    // first move
    onMapMove(this.map.getBounds());
  }

  private initTileLayer(tiles: L.TileLayer | undefined, map: L.Map) {
    tiles = tileLayer(
      `${environment.tileProviderLink}?access-token=${environment.accessToken}`,
      {}
    );
    tiles.addTo(map!);
    map.attributionControl.addAttribution(
      SundownerMapService.LEAFLET_ATTRIBUTION
    );
  }

  private initMapEvents(map: L.Map, onMapMove: (bounds: LatLngBounds) => void) {
    map.on('moveend', () => {
      onMapMove(this.map!.getBounds());
    });

    map.on('click', (e: L.LeafletMouseEvent) => {
      this.closeSpotPreview();
    });
  }

  public markSpots(newSpots: MapSpotTO[]) {
    if (!this.map || !this.markerLayer) {
      return;
    }

    // remove unseen spots
    const spotsToRemove = [...this.currentSpots.values()]
      .filter(
        (spot) =>
          !newSpots.find((newSpot) => newSpot.id === spot.data.id) &&
          spot.data.id !== this.selectedSpot?.data.id // do not remove selected
      )
      .map((spot) => spot.data);
    this.removeSpotMarkers(spotsToRemove);

    // add new spots
    const spotsToAdd = newSpots.filter(
      (newSpot) => !this.currentSpots.has(newSpot.id)
    );
    this.addSpotMarkers(spotsToAdd);

    this.markerLayer.addTo(this.map!);
  }

  private createIcon(spot: MapSpotTO) {
    return new DivIcon({
      className: 'spot-marker',
      html: this._spotMarkerService.getSpotMarkerHTML(spot),
    });
  }

  private removeSpotMarkers(spotsToRemove: MapSpotTO[]) {
    for (const spot of spotsToRemove) {
      this.removeSpotMarker(spot);
    }
  }

  private removeSpotMarker(spot: MapSpotTO) {
    const spotToRemove = this.currentSpots.get(spot.id);
    if (spotToRemove) {
      this.markerLayer?.removeLayer(spotToRemove.marker);
      this.currentSpots.delete(spotToRemove.data.id);
    }
  }

  private addSpotMarkers(spots: MapSpotTO[]) {
    for (const spot of spots) {
      this.addSpotMarker(spot);
    }
  }

  private addSpotMarker(spot: MapSpotTO) {
    const spotMarker = marker([spot.location.lat, spot.location.lng], {
      icon: this.createIcon(spot),
    });

    const spotToAdd = {
      data: spot,
      marker: spotMarker,
    };

    spotToAdd.marker
      .addTo(this.markerLayer!)
      .on('click', () => this.openSpotPreview(spotToAdd));
    this.currentSpots.set(spot.id, spotToAdd);
  }

  private openSpotPreview(spot: MapSpot) {
    if (!this.map) {
      return;
    }

    this.selectedSpot = spot;

    const latlng = spot.data.location;
    const bounds = this.map.getBounds();
    const mapHeight = bounds.getNorth() - bounds.getSouth();
    const newCenterLat = latlng.lat - mapHeight / 4.5; // upper half
    this.map.setView([newCenterLat, latlng.lng]);
    spot.marker.getElement()?.classList.add('selected-spot');
  }

  private closeSpotPreview() {
    if (!this.selectedSpot) {
      return;
    }

    this.selectedSpot.marker.getElement()?.classList.remove('selected-spot');
    this.selectedSpot = undefined;
  }
}

/**  This is a simple interface to hold the data and the marker for a spot. */
interface MapSpot {
  /** The map spot data */
  data: MapSpotTO;
  /** The marker on the map */
  marker: L.Marker;
}
