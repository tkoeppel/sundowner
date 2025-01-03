import { Component, OnInit } from '@angular/core';
import { MapSpotTO, SpotsService } from '../../../../gensrc';
import { BehaviorSubject, debounceTime, firstValueFrom } from 'rxjs';
import { MapBounds } from './sundowner-map/MapBounds';
import { SundownerMapComponent } from './sundowner-map/sundowner-map.component';

@Component({
  selector: 'app-sundowner',
  templateUrl: './sundowner.component.html',
  styleUrl: './sundowner.component.scss',
  standalone: true,
  imports: [SundownerMapComponent],
})
export class SundownerComponent implements OnInit {
  private readonly MAX_POINTS = 10;

  private bounds$: BehaviorSubject<MapBounds> = new BehaviorSubject({
    minX: 0,
    minY: 0,
    maxX: 0,
    maxY: 0,
  });

  public readonly START_POS = {
    lat: 48.7979389287977,
    lng: 9.800386608684917,
  };

  public spots: MapSpotTO[] = [];

  constructor(private _spotsService: SpotsService) {
    // nothing to do
  }

  async ngOnInit(): Promise<void> {
    // subscribe to bound changes
    this.bounds$
      .pipe(debounceTime(350)) //
      .subscribe(async (bounds) => {
        this.spots = await firstValueFrom(
          this._spotsService.getPointsInView(
            bounds.minX,
            bounds.minY,
            bounds.maxX,
            bounds.maxY,
            this.MAX_POINTS
          )
        );
      });
  }

  public async handleMapMove(bounds: MapBounds) {
    this.bounds$.next(bounds);
  }
}
