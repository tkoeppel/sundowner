import {
  ApplicationRef,
  ComponentRef,
  createComponent,
  EnvironmentInjector,
  Injectable,
  Injector,
} from '@angular/core';
import { SpotMarkerComponent } from '../../pages/sundowner/sundowner-map/spot-marker/spot-marker.component';

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

  private elements: HTMLElement[] = [];
  private refs: ComponentRef<unknown>[] = [];

  getSpotMarkerHTML(avgRating: number, name: string): HTMLElement {
    const element = document.createElement('div');
    const component = createComponent(SpotMarkerComponent, {
      elementInjector: this.injector,
      environmentInjector: this.environmentInjector,
      hostElement: element,
    });
    this.applicationRef.attachView(component.hostView);
    component.instance.avgRating = avgRating;
    component.instance.name = name;

    // collect for removing onDestroy
    this.refs.push(component);
    this.elements.push(element);
    return element;
  }

  cleanup(): void {
    this.refs.splice(0).forEach((ref) => ref.destroy());
    this.elements.splice(0).forEach((element) => element.remove());
  }
}
