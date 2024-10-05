import { Component, EventEmitter, Input, Output } from '@angular/core';
import {
  LatLngBounds,
  LatLngExpression,
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
export class SundownerMapComponent {
  @Output()
  public onMapMove: EventEmitter<LatLngBounds> = new EventEmitter();

  @Input()
  public currentPos: LatLngExpression = [0, 0];

  @Input()
  public spots: MapSpotTO[] = [];

  private map: L.Map | undefined;

  private tiles: L.TileLayer | undefined;

  constructor() {
    // nothing to do
  }

  ngOnInit(): void {
    this.map = map('map', {
      center: this.currentPos,
      zoom: 50,
    });

    this.tiles = tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 19,
        attribution:
          '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      }
    );

    this.tiles.addTo(this.map);

    this.onMapMove.emit(this.map.getBounds());
  }

  public markSpots() {
    if (!this.map || !this.tiles) {
      return;
    }

    for (let spot of this.spots) {
      marker([spot.location.lat, spot.location.lng]).addTo(this.map);
    }
  }
}
