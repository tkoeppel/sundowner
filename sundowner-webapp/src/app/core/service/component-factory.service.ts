import {
  createComponent,
  EnvironmentInjector,
  Injector,
  ComponentRef,
  Type,
  ApplicationRef,
} from '@angular/core';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ComponentFactoryService {
  constructor(
    private injector: Injector,
    private environmentInjector: EnvironmentInjector,
    private applicationRef: ApplicationRef
  ) {
    // nothing to do
  }

  createComponent<T>(
    component: Type<T>,
    hostElement: HTMLElement
  ): ComponentRef<T> {
    const componentRef = createComponent(component, {
      elementInjector: this.injector,
      environmentInjector: this.environmentInjector,
      hostElement: hostElement,
    });
    this.applicationRef.attachView(componentRef.hostView);

    return componentRef;
  }
}
