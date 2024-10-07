import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import {
  LatLngBounds,
  LatLngExpression,
  layerGroup,
  map,
  marker,
  tileLayer,
} from 'leaflet';
import { MapSpotTO } from '../../../../../gensrc';
@Component({
  selector: 'app-sundowner-map',
  standalone: true,
  imports: [],
  templateUrl: './sundowner-map.component.html',
  styleUrl: './sundowner-map.component.scss',
})
export class SundownerMapComponent implements OnInit {
  @Output()
  public onMapMove: EventEmitter<LatLngBounds> = new EventEmitter();

  @Input()
  public currentPos: LatLngExpression = [0, 0];

  private _spots: MapSpotTO[] = [];

  @Input()
  public set spots(mapSpots: MapSpotTO[]) {
    this._spots = mapSpots;
    this.markSpots();
  }

  private map: L.Map | undefined;

  private tiles: L.TileLayer | undefined;

  private markerLayer: L.LayerGroup | undefined;

  constructor() {
    // nothing to do
  }

  ngOnInit(): void {
    this.map = map('map', {
      center: this.currentPos,
      zoom: 16, // initial zoom
    });

    this.tiles = tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 19,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );

    this.markerLayer = layerGroup();

    this.tiles.addTo(this.map);

    this.onMapMove.emit(this.map.getBounds());

    this.map.on('moveend', () => {
      this.onMapMove.emit(this.map?.getBounds());
    });
  }

  private markSpots() {
    if (!this.map || !this.markerLayer) {
      return;
    }

    // reset all markers
    this.markerLayer.clearLayers();

    // add markers to layers
    for (let spot of this._spots) {
      marker([spot.location.lat, spot.location.lng]).addTo(this.markerLayer);
    }

    // add to map
    this.markerLayer.addTo(this.map);
  }
}
