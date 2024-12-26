import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-spot-marker',
  templateUrl: './spot-marker.component.html',
  styleUrl: './spot-marker.component.scss',
  standalone: true,
})
export class SpotMarkerComponent {
  @Input()
  public avgRating: number | undefined;

  @Input()
  public name: string | undefined;

  public getTransform() {
    if (this.avgRating) {
      const percentage = (this.avgRating / 10) * 100;
      return `rotate(${percentage * 3.6}deg)`;
    }
    return;
  }
}
