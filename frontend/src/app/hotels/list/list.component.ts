import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../api.service';

@Component({
  selector: 'app-hotel-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class HotelListComponent implements OnInit {
  hotels: any[] = [];
  loading = true;

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.api.getAllHotels().subscribe({
      next: (res) => {
        this.hotels = res.data ?? [];
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }
}
