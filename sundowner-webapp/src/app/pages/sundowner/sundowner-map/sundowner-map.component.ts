import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { LatLngExpression } from 'leaflet';
import { CoordinateTO, MapSpotTO } from '../../../../../gensrc';
import { MapService } from '../../../core/services/map.service';
import { MapBounds } from './MapBounds';
import { SpotMarkerComponent } from './spot-marker/spot-marker.component';
@Component({
  selector: 'app-sundowner-map',
  templateUrl: './sundowner-map.component.html',
  styleUrl: './sundowner-map.component.scss',
  standalone: true,
})
export class SundownerMapComponent implements OnInit {
  @Output()
  public onMapMove: EventEmitter<MapBounds> = new EventEmitter();

  @Input()
  public currentPos: CoordinateTO = { lat: 0, lng: 0 };

  private _spots: MapSpotTO[] = [];

  @Input()
  public set spots(mapSpots: MapSpotTO[]) {
    this._spots = mapSpots;
    this._mapService.markSpots(mapSpots);
  }

  constructor(private _mapService: MapService) {
    // nothing to do
  }

  ngOnInit(): void {
    this._mapService.createMap('map', this.currentPos, (bounds) => {
      // lat = y, lng = x
      const min = bounds.getSouthWest();
      const max = bounds.getNorthEast();

      this.onMapMove.emit({
        minX: min.lng,
        maxX: max.lng,
        minY: min.lat,
        maxY: max.lat,
      });
    });
  }
}
