import { Component } from '@angular/core';
import { MapSpotTO } from '../../../../../../gensrc';
import { SpotMarkerComponent } from '../spot-marker/spot-marker.component';
import { ComponentFactoryService } from '../../../../core/service/component-factory.service';

@Component({
  selector: 'app-spot-view',
  imports: [],
  templateUrl: './spot-view.component.html',
  styleUrl: './spot-view.component.scss',
})
export class SpotViewComponent {
  constructor(private componentFactoryService: ComponentFactoryService) {
    // nothing to do
  }

  public getSpotMarkerHTML(spot: MapSpotTO): HTMLElement {
    const element = document.createElement('div');
    const component = this.componentFactoryService.createComponent(
      SpotMarkerComponent,
      element
    );
    component.instance.spot = spot;
    return element;
  }
}
