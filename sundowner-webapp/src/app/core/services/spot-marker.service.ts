import {
  ApplicationRef,
  ComponentRef,
  createComponent,
  EnvironmentInjector,
  Injectable,
  Injector,
} from '@angular/core';
import { SpotMarkerComponent } from '../../pages/sundowner/sundowner-map/spot-marker/spot-marker.component';
import { MapSpotTO } from '../../../../gensrc';

@Injectable({
  providedIn: 'root',
})
export class SpotMarkerService {
  constructor(
    private injector: Injector,
    private environmentInjector: EnvironmentInjector,
    private applicationRef: ApplicationRef
  ) {
    // nothing to do
  }

  getSpotMarkerHTML(spot: MapSpotTO): HTMLElement {
    const element = document.createElement('div');
    const component = createComponent(SpotMarkerComponent, {
      elementInjector: this.injector,
      environmentInjector: this.environmentInjector,
      hostElement: element,
    });
    this.applicationRef.attachView(component.hostView);
    component.instance.avgRating = spot.avgRating;
    component.instance.name = spot.name;
    return element;
  }
}
