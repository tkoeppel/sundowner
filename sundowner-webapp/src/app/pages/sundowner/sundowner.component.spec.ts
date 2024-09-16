import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SundownerComponent } from './sundowner.component';

describe('SundownerComponent', () => {
  let component: SundownerComponent;
  let fixture: ComponentFixture<SundownerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SundownerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SundownerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
