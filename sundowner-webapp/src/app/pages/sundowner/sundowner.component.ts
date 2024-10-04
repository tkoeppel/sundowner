import { Component, OnInit } from '@angular/core';
import { SundownerMapComponent } from './sundowner-map/sundowner-map.component';
import { MapSpotTO, SpotsService } from '../../../../gensrc';
import { LatLngBounds, LatLngExpression } from 'leaflet';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-sundowner',
  standalone: true,
  imports: [SundownerMapComponent],
  templateUrl: './sundowner.component.html',
  styleUrl: './sundowner.component.scss',
})
export class SundownerComponent {
  private readonly MAX_POINTS = 10;

  public readonly START_POS: LatLngExpression = [
    48.7979389287977, 9.800386608684917,
  ];

  public spots: MapSpotTO[] = [];

  constructor(private _spotsService: SpotsService) {}

  public async handleMapMove(bounds: LatLngBounds) {
    // lat = y, lng = x
    const min = bounds.getSouthWest();
    const max = bounds.getNorthEast();

    this.spots = await firstValueFrom(
      this._spotsService.getPointsInView(
        min.lng,
        min.lat,
        max.lng,
        max.lat,
        this.MAX_POINTS
      )
    );
  }
}
