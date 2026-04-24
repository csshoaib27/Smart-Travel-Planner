import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from '../../api.service';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-itinerary-generator',
  templateUrl: './generator.component.html',
  styleUrls: ['./generator.component.css']
})
export class ItineraryGeneratorComponent implements OnInit {
  destinations: any[] = [];
  loading = false;
  itinerary = {
    title: '',
    description: '',
    startDate: '',
    endDate: '',
    packageType: 'Solo',
    currency: 'USD',
    isPublic: false,
    destinationId: null
  };
  packageTypes = ['Solo', 'Couple', 'Family', 'Adventure'];

  constructor(
    private api: ApiService,
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.api.getAllDestinations().subscribe({
      next: (res) => { this.destinations = res.data ?? []; }
    });
  }

  get numberOfDays(): number {
    if (!this.itinerary.startDate || !this.itinerary.endDate) return 0;
    const diff = new Date(this.itinerary.endDate).getTime() - new Date(this.itinerary.startDate).getTime();
    return Math.max(0, Math.floor(diff / (1000 * 60 * 60 * 24)) + 1);
  }

  onSubmit(): void {
    if (!this.itinerary.title || !this.itinerary.startDate || !this.itinerary.endDate) {
      this.toastr.warning('Please fill in all required fields.');
      return;
    }
    if (this.numberOfDays < 1) {
      this.toastr.warning('End date must be after start date.');
      return;
    }
    this.loading = true;
    const payload = { ...this.itinerary, userId: this.authService.getUserId(), numberOfDays: this.numberOfDays };
    this.api.createItinerary(payload).subscribe({
      next: (res) => {
        this.toastr.success('Itinerary created!');
        this.router.navigate(['/itineraries', res.data?.itineraryId]);
      },
      error: () => {
        this.toastr.error('Failed to create itinerary.');
        this.loading = false;
      }
    });
  }
}
