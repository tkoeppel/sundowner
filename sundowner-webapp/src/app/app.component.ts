import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
    imports: [RouterModule]
})
export class AppComponent {
  private readonly TIMEOUT = 1000;

  constructor(private router: Router) {}

  ngOnInit(): void {
    // make start screen appear for 1s
    setTimeout(() => {
      this.router.navigate(['/sundowner']);
    }, this.TIMEOUT);
  }
}
