import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from '../../api.service';

@Component({
  selector: 'app-itinerary-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class ItineraryDetailComponent implements OnInit {
  itinerary: any = null;
  loading = true;

  constructor(
    private api: ApiService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.api.getItineraryById(id).subscribe({
      next: (res) => { this.itinerary = res.data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  delete(): void {
    if (!confirm('Delete this itinerary?')) return;
    this.api.deleteItinerary(this.itinerary.itineraryId).subscribe({
      next: () => {
        this.toastr.success('Itinerary deleted.');
        this.router.navigate(['/']);
      },
      error: () => { this.toastr.error('Failed to delete.'); }
    });
  }
}
