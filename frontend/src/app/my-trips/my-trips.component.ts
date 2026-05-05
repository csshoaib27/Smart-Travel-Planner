import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from '../api.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-my-trips',
  templateUrl: './my-trips.component.html'
})
export class MyTripsComponent implements OnInit {
  activeTab = 'bookings';
  bookings: any[] = [];
  itineraries: any[] = [];
  bookingsLoading = true;
  itinerariesLoading = true;
  bookingsError = '';
  itinerariesError = '';

  constructor(
    private api: ApiService,
    public authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }
    const userId = this.authService.getUserId();
    this.api.getUserBookings(userId).subscribe({
      next: (res) => { this.bookings = res.data ?? []; this.bookingsLoading = false; },
      error: (err) => {
        this.bookingsError = err?.error?.message || 'Failed to load bookings.';
        this.bookingsLoading = false;
      }
    });
    this.api.getUserItineraries(userId).subscribe({
      next: (res) => { this.itineraries = res.data ?? []; this.itinerariesLoading = false; },
      error: (err) => {
        this.itinerariesError = err?.error?.message || 'Failed to load itineraries.';
        this.itinerariesLoading = false;
      }
    });
  }

  cancelBooking(bookingId: number): void {
    if (!confirm('Cancel this booking?')) return;
    this.api.cancelBooking(bookingId).subscribe({
      next: () => {
        this.bookings = this.bookings.map(b =>
          b.bookingId === bookingId ? { ...b, status: 'Cancelled' } : b
        );
        this.toastr.success('Booking cancelled.');
      },
      error: () => this.toastr.error('Could not cancel booking.')
    });
  }

  statusBadge(status: string): string {
    const map: Record<string, string> = {
      Confirmed: 'success', Pending: 'warning', Cancelled: 'danger', Completed: 'secondary'
    };
    return map[status] ?? 'secondary';
  }
}
