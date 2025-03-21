import {
  ApplicationRef,
  Component,
  createComponent,
  EnvironmentInjector,
  Injector,
} from '@angular/core';
import { MapSpotTO } from '../../../../../../gensrc';
import { SpotMarkerComponent } from '../spot-marker/spot-marker.component';

@Component({
    selector: 'app-spot-view',
    imports: [],
    templateUrl: './spot-view.component.html',
    styleUrl: './spot-view.component.scss'
})
export class SpotViewComponent {
  constructor(
    private injector: Injector,
    private environmentInjector: EnvironmentInjector,
    private applicationRef: ApplicationRef
  ) {
    // nothing to do
  }

  public getSpotMarkerHTML(spot: MapSpotTO): HTMLElement {
    const element = document.createElement('div');
    const component = createComponent(SpotMarkerComponent, {
      elementInjector: this.injector,
      environmentInjector: this.environmentInjector,
      hostElement: element,
    });
    this.applicationRef.attachView(component.hostView);
    component.instance.spot = spot;
    return element;
  }
}
