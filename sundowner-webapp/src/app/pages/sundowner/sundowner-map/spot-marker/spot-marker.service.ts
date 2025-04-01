import { Injectable } from '@angular/core';
import { SpotMarkerComponent } from './spot-marker.component';
import { MapSpotTO } from '../../../../../../gensrc';
import { ComponentFactoryService } from '../../../../core/service/component-factory.service';

@Injectable({
  providedIn: 'root',
})
export class SpotMarkerService {
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
