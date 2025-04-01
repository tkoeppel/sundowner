import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CoordinateTO, MapSpotTO } from '../../../../../gensrc';
import { SundownerMapService } from './sundowner-map.service';
import { MapBounds } from './MapBounds';
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

  @Input()
  public set spots(mapSpots: MapSpotTO[]) {
    this._mapService.markSpots(mapSpots);
  }

  constructor(private _mapService: SundownerMapService) {
    // nothing to do
  }

  ngOnInit(): void {
    this._mapService.initMap('map', this.currentPos, (bounds) => {
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
