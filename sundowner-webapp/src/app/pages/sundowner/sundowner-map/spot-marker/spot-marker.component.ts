import { Component, Input, OnInit } from '@angular/core';
import { MapSpotTO } from '../../../../../../gensrc';
@Component({
  selector: 'app-spot-marker',
  templateUrl: './spot-marker.component.html',
  styleUrl: './spot-marker.component.scss',
  standalone: true,
})
export class SpotMarkerComponent implements OnInit {
  constructor() {
    // nothing to do
  }

  @Input()
  public spot: MapSpotTO | undefined;

  public circumference = 2 * Math.PI * 14;
  public finalDashoffset = 0;

  ngOnInit() {
    if (this.spot) {
      this.finalDashoffset =
        this.circumference * (1 - this.spot.avgRating / 10);
    }
  }
}
