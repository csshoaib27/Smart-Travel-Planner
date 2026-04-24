import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from '../../api.service';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-hotel-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class HotelDetailComponent implements OnInit {
  hotel: any = null;
  reviews: any[] = [];
  loading = true;
  booking = { checkInDate: '', checkOutDate: '', numberOfGuests: 1, numberOfRooms: 1, roomType: 'Double' };
  booking_loading = false;

  constructor(
    private api: ApiService,
    private route: ActivatedRoute,
    public authService: AuthService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.api.getHotelById(id).subscribe({
      next: (res) => { this.hotel = res.data; this.loading = false; },
      error: () => { this.loading = false; }
    });
    this.api.getReviewsByHotel(id).subscribe({
      next: (res) => { this.reviews = res.data ?? []; }
    });
  }

  get nights(): number {
    if (!this.booking.checkInDate || !this.booking.checkOutDate) return 0;
    const diff = new Date(this.booking.checkOutDate).getTime() - new Date(this.booking.checkInDate).getTime();
    return Math.max(0, Math.floor(diff / (1000 * 60 * 60 * 24)));
  }

  get totalPrice(): number {
    return this.nights * this.hotel?.pricePerNight * this.booking.numberOfRooms;
  }

  bookNow(): void {
    if (!this.authService.isAuthenticated()) {
      this.toastr.warning('Please login to make a booking.');
      return;
    }
    if (!this.booking.checkInDate || !this.booking.checkOutDate || this.nights < 1) {
      this.toastr.warning('Please select valid check-in and check-out dates.');
      return;
    }
    this.booking_loading = true;
    const payload = {
      userId: this.authService.getUserId(),
      hotelId: this.hotel.hotelId,
      destinationId: this.hotel.destinationId,
      ...this.booking,
      numberOfNights: this.nights,
      totalPrice: this.totalPrice
    };
    this.api.createBooking(payload).subscribe({
      next: () => {
        this.toastr.success('Booking confirmed!');
        this.booking_loading = false;
      },
      error: () => {
        this.toastr.error('Booking failed. Please try again.');
        this.booking_loading = false;
      }
    });
  }
}
