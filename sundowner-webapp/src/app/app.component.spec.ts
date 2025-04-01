import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { RouterModule } from '@angular/router'; // Import RouterModule

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let router: Router;
  let location: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterModule.forRoot([{ path: 'sundowner', component: AppComponent }]),
      ],
    }).compileComponents();

    // init router
    router = TestBed.inject(Router);
    location = TestBed.inject(Location);
    router.initialNavigation();

    // init component
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should navigate to sundowner after timeout`, () => {
    fixture.detectChanges();
    return new Promise((resolve) => {
      setTimeout(() => {
        expect(location.path()).toBe('/sundowner');
        resolve(null); // Resolve the Promise when the assertion is done
      }, 1000);
    });
  });
});
