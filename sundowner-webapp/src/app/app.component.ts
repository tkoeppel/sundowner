import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { SundownerComponent } from "./pages/sundowner/sundowner.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SundownerComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  private readonly TIMEOUT = 1000;

  constructor(private router: Router) { }

  ngOnInit(): void {

    // make start screen appear for 1s
    setTimeout(() => {
      this.router.navigate(['/sundowner']);  
    }, this.TIMEOUT);
  }
}
