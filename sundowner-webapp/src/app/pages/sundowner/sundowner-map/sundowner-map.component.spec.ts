import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SundownerMapComponent } from './sundowner-map.component';

describe('SundownerMapComponent', () => {
  let component: SundownerMapComponent;
  let fixture: ComponentFixture<SundownerMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SundownerMapComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SundownerMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
