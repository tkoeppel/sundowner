import { Component } from '@angular/core';
import { MapSpotTO, SpotsService } from '../../../../gensrc';
import { firstValueFrom } from 'rxjs';
import { MapBounds } from './sundowner-map/MapBounds';
import { SundownerMapComponent } from './sundowner-map/sundowner-map.component';

@Component({
  selector: 'app-sundowner',
  templateUrl: './sundowner.component.html',
  styleUrl: './sundowner.component.scss',
  standalone: true,
  imports: [SundownerMapComponent],
})
export class SundownerComponent {
  private readonly MAX_POINTS = 10;

  public readonly START_POS = {
    lat: 48.7979389287977,
    lng: 9.800386608684917,
  };

  public spots: MapSpotTO[] = [];

  constructor(private _spotsService: SpotsService) {}

  public async handleMapMove(bounds: MapBounds) {
    this.spots = await firstValueFrom(
      this._spotsService.getPointsInView(
        bounds.minX,
        bounds.minY,
        bounds.maxX,
        bounds.maxY,
        this.MAX_POINTS
      )
    );
  }
}
