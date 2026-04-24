import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../api.service';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  stats: any = null;
  users: any[] = [];
  bookings: any[] = [];
  activeTab = 'stats';
  loading = true;

  constructor(private api: ApiService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    if (!this.authService.isAdmin()) {
      this.router.navigate(['/']);
      return;
    }
    this.api.getSystemStats().subscribe({
      next: (res) => { this.stats = res.data; this.loading = false; },
      error: () => { this.loading = false; }
    });
    this.api.getAllUsers().subscribe({
      next: (res) => { this.users = res.data ?? []; }
    });
    this.api.getAllBookings().subscribe({
      next: (res) => { this.bookings = res.data ?? []; }
    });
  }
}
