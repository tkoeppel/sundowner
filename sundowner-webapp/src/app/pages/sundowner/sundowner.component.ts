import { Component } from '@angular/core';
import * as L from 'leaflet'; 
import { LatLngExpression } from 'leaflet';

@Component({
  selector: 'app-sundowner',
  standalone: true,
  imports: [],
  templateUrl: './sundowner.component.html',
  styleUrl: './sundowner.component.scss'
})
export class SundownerComponent {
  private readonly START_POS: LatLngExpression = [48.7979389287977, 9.800386608684917];
  private  map: L.Map | undefined;

  ngOnInit(): void {
    // Optional: Fetch data for markers or other map features from your service
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  private initMap(): void {
    this.map = L.map('map', { 
      center: this.START_POS, // Zeiselberg Schwäbisch Gmünd
      zoom: 50
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'  
    });

    L.marker(this.START_POS).addTo(this.map);

    tiles.addTo(this.map);
  }
}
