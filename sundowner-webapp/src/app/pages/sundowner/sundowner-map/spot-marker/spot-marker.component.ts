import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-spot-marker',
  templateUrl: './spot-marker.component.html',
  styleUrl: './spot-marker.component.scss',
  standalone: true,
})
export class SpotMarkerComponent implements OnInit {
  @Input()
  public avgRating: number | undefined;

  @Input()
  public name: string | undefined;

  public circumference = 2 * Math.PI * 14;
  public finalDashoffset = 0;

  ngOnInit() {
    if (this.avgRating) {
      this.finalDashoffset = this.circumference * (1 - this.avgRating / 10);
    }
  }
}
