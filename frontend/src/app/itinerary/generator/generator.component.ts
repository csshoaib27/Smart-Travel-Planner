import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
  downloading = false;

  step: 'form' | 'selection' | 'result' = 'form';
  generatedItinerary: any = null;
  selectedPackage: string | null = null;
  selectedBudget: string | null = null;
  suggestedHotels: any[] = [];

  form = {
    title: '',
    description: '',
    startDate: '',
    endDate: '',
    destinationId: null as number | null,
    packageType: 'Solo',
    budgetTier: 'Economic',
    currency: 'INR',
    isPublic: false
  };

  packageTypes = [
    { value: 'Solo',      icon: '🧳', label: 'Solo',      desc: 'Independent solo travel — explore at your own pace' },
    { value: 'Couple',    icon: '💑', label: 'Couple',     desc: 'Romantic getaway — intimate experiences for two' },
    { value: 'Family',    icon: '👨‍👩‍👧', label: 'Family',    desc: 'Fun for everyone — kid-friendly activities & stays' },
    { value: 'Adventure', icon: '🏔️', label: 'Adventure',  desc: 'Thrilling outdoor experiences & extreme sports' }
  ];

  budgetTiers = [
    { value: 'Economic', icon: '💚', label: 'Economic', cost: '₹1,500/day', desc: 'Budget stays & street food' },
    { value: 'Premium',  icon: '💙', label: 'Premium',  cost: '₹5,000/day', desc: 'Comfortable hotels & restaurants' },
    { value: 'Luxury',   icon: '💛', label: 'Luxury',   cost: '₹15,000/day', desc: '5-star hotels & fine dining' }
  ];

  currencies = [
    { code: 'INR', label: '₹ INR — Indian Rupee' },
    { code: 'USD', label: '$ USD — US Dollar' },
    { code: 'EUR', label: '€ EUR — Euro' },
    { code: 'GBP', label: '£ GBP — British Pound' },
    { code: 'AED', label: 'AED — UAE Dirham' },
    { code: 'SGD', label: 'S$ SGD — Singapore Dollar' },
    { code: 'AUD', label: 'A$ AUD — Australian Dollar' },
    { code: 'JPY', label: '¥ JPY — Japanese Yen' }
  ];

  constructor(
    private api: ApiService,
    private authService: AuthService,
    private toastr: ToastrService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.api.getAllDestinations().subscribe({
      next: (res) => {
        this.destinations = res.data ?? [];
        const paramId = this.route.snapshot.queryParamMap.get('destinationId');
        if (paramId) {
          this.form.destinationId = Number(paramId);
        }
      }
    });
  }

  get numberOfDays(): number {
    if (!this.form.startDate || !this.form.endDate) return 0;
    const diff = new Date(this.form.endDate).getTime() - new Date(this.form.startDate).getTime();
    return Math.max(0, Math.floor(diff / (1000 * 60 * 60 * 24)) + 1);
  }

  get selectedDestinationName(): string {
    const d = this.destinations.find(x => x.destinationId === this.form.destinationId);
    return d ? `${d.name}, ${d.country}` : '';
  }

  estimatedTotal(budgetTier: string): number {
    const rates: Record<string, number> = { Economic: 1500, Premium: 5000, Luxury: 15000 };
    return (rates[budgetTier] ?? 1500) * Math.max(1, this.numberOfDays);
  }

  showOptions(): void {
    if (!this.form.title || !this.form.startDate || !this.form.endDate) {
      this.toastr.warning('Please fill in Trip Title, Start Date and End Date.');
      return;
    }
    if (this.numberOfDays < 1) {
      this.toastr.warning('End date must be on or after start date.');
      return;
    }
    this.step = 'selection';
    this.selectedPackage = null;
    this.selectedBudget = null;
  }

  selectCombination(pkg: string, budget: string): void {
    this.selectedPackage = pkg;
    this.selectedBudget = budget;
    this.form.packageType = pkg;
    this.form.budgetTier = budget;
    this.loading = true;
    this.suggestedHotels = [];

    const payload = {
      ...this.form,
      userId: this.authService.getUserId(),
      numberOfDays: this.numberOfDays
    };

    this.api.createItinerary(payload).subscribe({
      next: (res) => {
        this.generatedItinerary = res.data;
        this.step = 'result';
        this.loading = false;
        this.toastr.success('Your day-by-day itinerary is ready!');
        if (this.selectedDestinationName) {
          this.loadSuggestedHotels(this.selectedDestinationName, budget);
        }
      },
      error: (err) => {
        console.error('Create itinerary error:', err);
        this.toastr.error('Failed to generate itinerary. Please try again.');
        this.loading = false;
        this.selectedPackage = null;
        this.selectedBudget = null;
      }
    });
  }

  private loadSuggestedHotels(destinationName: string, budgetTier: string): void {
    this.api.searchGoogleHotels(destinationName, budgetTier, this.form.destinationId).subscribe({
      next: (res) => { this.suggestedHotels = res.data ?? []; },
      error: () => { this.suggestedHotels = []; }
    });
  }

  stars(rating: number | null): string {
    if (!rating) return '☆☆☆☆☆';
    const full  = Math.round(rating);
    return '★'.repeat(full) + '☆'.repeat(Math.max(0, 5 - full));
  }

  downloadPdf(): void {
    if (!this.generatedItinerary) return;
    this.downloading = true;

    const days = this.sortedDays(this.generatedItinerary.days).map((d: any) => ({
      dayNumber: d.dayNumber,
      activities: d.activities,
      note: d.notes,
      budget: d.estimatedBudget
    }));

    const hotels = this.suggestedHotels.map((h: any) => ({
      name: h.name,
      starRating: h.rating ?? null,
      pricePerNight: null,
      priceRange: h.priceRange,
      reviewCount: h.reviewCount
    }));

    const request = {
      title: this.generatedItinerary.title,
      destinationName: this.selectedDestinationName || 'N/A',
      startDate: this.generatedItinerary.startDate,
      endDate: this.generatedItinerary.endDate,
      currency: this.generatedItinerary.currency,
      packageType: this.generatedItinerary.packageType,
      budgetTier: this.generatedItinerary.budgetTier,
      numberOfDays: this.generatedItinerary.numberOfDays,
      totalCost: this.generatedItinerary.totalEstimatedCost,
      days,
      hotels
    };

    this.api.downloadItineraryPlanPdf(request).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `${request.title.replace(/[^a-zA-Z0-9]/g, '_')}.pdf`;
        a.click();
        window.URL.revokeObjectURL(url);
        this.downloading = false;
        this.toastr.success('PDF downloaded!');
      },
      error: () => {
        this.toastr.error('PDF download failed. Please try again.');
        this.downloading = false;
      }
    });
  }

  reset(): void {
    this.step = 'form';
    this.generatedItinerary = null;
    this.selectedPackage = null;
    this.selectedBudget = null;
    this.suggestedHotels = [];
    this.form = {
      title: '', description: '', startDate: '', endDate: '',
      destinationId: null, packageType: 'Solo',
      budgetTier: 'Economic', currency: 'INR', isPublic: false
    };
  }

  sortedDays(days: any[]): any[] {
    if (!days) return [];
    return [...days].sort((a, b) => a.dayNumber - b.dayNumber);
  }
}
