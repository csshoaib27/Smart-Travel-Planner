import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from '../../api.service';

@Component({
  selector: 'app-cost-calculator',
  templateUrl: './cost-calculator.component.html',
  styleUrls: ['./cost-calculator.component.css']
})
export class CostCalculatorComponent implements OnInit {
  request = {
    tripTitle: '',
    destinationName: '',
    startDate: '',
    endDate: '',
    currency: 'INR',
    numberOfPeople: 1,
    numberOfDays: 3,
    numberOfRooms: 1,
    hotelPricePerNight: 0,
    transportationCost: 0,
    foodCostPerDay: 0,
    activitiesCost: 0,
    otherCosts: 0
  };

  currencies = [
    { code: 'INR', label: '₹ INR' },
    { code: 'USD', label: '$ USD' },
    { code: 'EUR', label: '€ EUR' },
    { code: 'GBP', label: '£ GBP' },
    { code: 'AED', label: 'AED' },
    { code: 'SGD', label: 'S$ SGD' },
    { code: 'AUD', label: 'A$ AUD' },
    { code: 'JPY', label: '¥ JPY' }
  ];
  result: any = null;
  loading = false;
  downloading = false;
  destinations: any[] = [];

  constructor(
    private api: ApiService,
    private toastr: ToastrService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.api.getAllDestinations().subscribe({
      next: (res) => { this.destinations = res.data ?? []; }
    });

    this.route.queryParams.subscribe(params => {
      if (params['dest']) {
        this.request.destinationName = params['dest'];
      }
    });
  }

  calculate(): void {
    this.loading = true;
    this.api.calculateCostPerPerson(this.request).subscribe({
      next: (res) => {
        this.result = res.data;
        this.loading = false;
      },
      error: () => {
        this.toastr.error('Calculation failed. Please try again.');
        this.loading = false;
      }
    });
  }

  downloadPdf(): void {
    this.downloading = true;
    this.api.downloadItineraryPdf(this.request).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        const filename = this.request.tripTitle?.trim()
          ? this.request.tripTitle.replace(/[^a-zA-Z0-9]/g, '_') + '.pdf'
          : 'itinerary.pdf';
        a.download = filename;
        a.click();
        window.URL.revokeObjectURL(url);
        this.toastr.success('PDF downloaded successfully!');
        this.downloading = false;
      },
      error: () => {
        this.toastr.error('Failed to generate PDF. Please try again.');
        this.downloading = false;
      }
    });
  }

  reset(): void {
    this.result = null;
    this.request = {
      tripTitle: '', destinationName: '', startDate: '', endDate: '',
      currency: 'INR', numberOfPeople: 1, numberOfDays: 3, numberOfRooms: 1,
      hotelPricePerNight: 0, transportationCost: 0,
      foodCostPerDay: 0, activitiesCost: 0, otherCosts: 0
    };
  }
}
