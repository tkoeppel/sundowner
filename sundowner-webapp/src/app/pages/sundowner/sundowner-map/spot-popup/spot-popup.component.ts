import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-spot-popup',
  standalone: true,
  imports: [],
  templateUrl: './spot-popup.component.html',
  styleUrl: './spot-popup.component.scss',
})
export class SpotPopupComponent {
  @Input()
  public set id(newId: number) {
    // call whole spot info
  }
}
