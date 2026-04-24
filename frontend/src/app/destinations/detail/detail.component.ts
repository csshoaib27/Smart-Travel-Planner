import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../../api.service';

@Component({
  selector: 'app-destination-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DestinationDetailComponent implements OnInit {
  destination: any = null;
  hotels: any[] = [];
  reviews: any[] = [];
  loading = true;

  constructor(private api: ApiService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.api.getDestinationById(id).subscribe({
      next: (res) => {
        this.destination = res.data;
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
    this.api.getHotelsByDestination(id).subscribe({
      next: (res) => { this.hotels = res.data ?? []; }
    });
    this.api.getReviewsByDestination(id).subscribe({
      next: (res) => { this.reviews = res.data ?? []; }
    });
  }
}
