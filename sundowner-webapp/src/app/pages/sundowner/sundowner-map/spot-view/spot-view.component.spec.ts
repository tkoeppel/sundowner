import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpotViewComponent } from './spot-view.component';

describe('SpotViewComponent', () => {
  let component: SpotViewComponent;
  let fixture: ComponentFixture<SpotViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpotViewComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SpotViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
