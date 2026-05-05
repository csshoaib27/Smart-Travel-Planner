import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../api.service';

@Component({
  selector: 'app-hotel-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class HotelListComponent implements OnInit {
  allHotels: any[] = [];
  hotels: any[] = [];
  countries: string[] = [];
  cities: string[] = [];
  loading = true;

  country = '';
  city = '';
  category = '';
  destinationType = '';

  categories = ['Budget', 'Premium', 'Luxury'];
  destinationTypes = ['Nature', 'Adventure', 'Budget', 'Luxury', 'Cultural', 'Beach', 'Mountain'];

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.api.getAllHotels().subscribe({
      next: (res) => {
        this.allHotels = res.data ?? [];
        this.countries = [...new Set<string>(
          this.allHotels.map((h: any) => h.country).filter((c: any) => !!c)
        )].sort();
        this.cities = [...new Set<string>(
          this.allHotels.map((h: any) => h.city).filter((c: any) => !!c)
        )].sort();
        this.applyFilters();
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  applyFilters(): void {
    this.hotels = this.allHotels.filter(h => {
      const countryMatch = !this.country || h.country === this.country;
      const cityMatch = !this.city || h.city === this.city;
      const categoryMatch = !this.category || this.getCategoryForHotel(h) === this.category;
      const typeMatch = !this.destinationType || h.travelType === this.destinationType;
      return countryMatch && cityMatch && categoryMatch && typeMatch;
    });
  }

  onCountryChange(): void {
    // Reset city when country changes and rebuild city list for the selected country
    this.city = '';
    const filteredByCountry = this.country
      ? this.allHotels.filter((h: any) => h.country === this.country)
      : this.allHotels;
    this.cities = [...new Set<string>(
      filteredByCountry.map((h: any) => h.city).filter((c: any) => !!c)
    )].sort();
    this.applyFilters();
  }

  getCategoryForHotel(h: any): string {
    const price = h.pricePerNight ?? 0;
    if (price > 20000) return 'Luxury';
    if (price > 6000) return 'Premium';
    return 'Budget';
  }

  clearFilters(): void {
    this.country = '';
    this.city = '';
    this.category = '';
    this.destinationType = '';
    this.cities = [...new Set<string>(
      this.allHotels.map((h: any) => h.city).filter((c: any) => !!c)
    )].sort();
    this.applyFilters();
  }
}
