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
  itineraries: any[] = [];
  filteredItineraries: any[] = [];
  activeTab = 'stats';
  loading = true;
  usersLoading = true;
  itinerariesLoading = true;
  usersError = '';
  itinerariesError = '';

  itinerarySearch = '';
  itineraryPackageFilter = '';
  itineraryBudgetFilter = '';
  expandedItineraryId: number | null = null;

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
      next: (res) => { this.users = res.data ?? []; this.usersLoading = false; },
      error: (err) => {
        this.usersError = err?.error?.message || 'Failed to load users.';
        this.usersLoading = false;
      }
    });
    this.api.getAllBookings().subscribe({
      next: (res) => { this.bookings = res.data?.bookings ?? res.data ?? []; }
    });
    this.api.getAllItineraries().subscribe({
      next: (res) => {
        this.itineraries = res.data ?? [];
        this.filteredItineraries = [...this.itineraries];
        this.itinerariesLoading = false;
      },
      error: (err) => {
        this.itinerariesError = err?.error?.message || 'Failed to load itineraries.';
        this.itinerariesLoading = false;
      }
    });
  }

  applyItineraryFilters(): void {
    const search = this.itinerarySearch.toLowerCase();
    this.filteredItineraries = this.itineraries.filter(it => {
      const matchSearch = !search ||
        it.title?.toLowerCase().includes(search) ||
        it.username?.toLowerCase().includes(search);
      const matchPkg = !this.itineraryPackageFilter || it.packageType === this.itineraryPackageFilter;
      const matchBudget = !this.itineraryBudgetFilter || it.budgetTier === this.itineraryBudgetFilter;
      return matchSearch && matchPkg && matchBudget;
    });
  }

  toggleItinerary(id: number): void {
    this.expandedItineraryId = this.expandedItineraryId === id ? null : id;
  }

  deleteItinerary(id: number): void {
    if (!confirm('Delete this itinerary? This cannot be undone.')) return;
    this.api.deleteAdminItinerary(id).subscribe({
      next: () => {
        this.itineraries = this.itineraries.filter(it => it.itineraryId !== id);
        this.applyItineraryFilters();
      }
    });
  }

  packageIcon(pkg: string): string {
    const icons: Record<string, string> = {
      Solo: '🧳', Couple: '💑', Family: '👨‍👩‍👧', Adventure: '🏔️'
    };
    return icons[pkg] ?? '✈️';
  }

  budgetColor(tier: string): string {
    const colors: Record<string, string> = {
      Economic: 'success', Premium: 'primary', Luxury: 'warning'
    };
    return colors[tier] ?? 'secondary';
  }
}
