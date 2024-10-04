import { Routes } from '@angular/router';
import { SundownerComponent } from './pages/sundowner/sundowner.component';

export const routes: Routes = [
  { path: '', redirectTo: '/sundowner', pathMatch: 'full' },
  { path: 'sundowner', component: SundownerComponent },
];
