import {
  ApplicationRef,
  ComponentRef,
  createComponent,
  EnvironmentInjector,
  Injectable,
  Injector,
} from '@angular/core';
import { SpotPopupComponent } from '../../pages/sundowner/sundowner-map/spot-popup/spot-popup.component';

@Injectable({
  providedIn: 'root',
})
export class SpotPopupService {
  constructor(
    private injector: Injector,
    private environmentInjector: EnvironmentInjector,
    private applicationRef: ApplicationRef
  ) {
    // nothing to do
  }

  private elements: HTMLElement[] = [];
  private refs: ComponentRef<unknown>[] = [];

  getSpotPopupHTML(id: number): HTMLElement {
    const element = document.createElement('div');
    const component = createComponent(SpotPopupComponent, {
      elementInjector: this.injector,
      environmentInjector: this.environmentInjector,
      hostElement: element,
    });
    this.applicationRef.attachView(component.hostView);
    component.instance.id = id;

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
