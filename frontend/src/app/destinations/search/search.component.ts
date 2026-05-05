import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../../api.service';

@Component({
  selector: 'app-destination-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class DestinationSearchComponent implements OnInit {
  destinations: any[] = [];
  countries: string[] = [];
  loading = false;
  country = '';
  travelType = '';
  budgetCategory = '';

  travelTypes = ['Nature', 'Adventure', 'Budget', 'Luxury', 'Cultural', 'Beach', 'Mountain'];
  budgetCategories = ['Budget', 'MidRange', 'Luxury'];

  onImageError(dest: any): void {
    dest.imageUrl = null;
  }

  constructor(private api: ApiService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.api.getDestinationCountries().subscribe({
      next: (res) => { this.countries = res.data ?? []; },
      error: () => {}
    });
    this.route.queryParams.subscribe(params => {
      this.country = params['country'] ?? '';
      this.search();
    });
  }

  search(): void {
    this.loading = true;
    this.api.searchDestinations(this.country, this.travelType, this.budgetCategory, '').subscribe({
      next: (res) => {
        this.destinations = res.data ?? [];
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  onCountryChange(): void {
    this.search();
  }

  clearFilters(): void {
    this.country = '';
    this.travelType = '';
    this.budgetCategory = '';
    this.search();
  }
}
