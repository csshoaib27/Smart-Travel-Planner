import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ApiService } from '../../api.service';

@Component({
  selector: 'app-cost-calculator',
  templateUrl: './cost-calculator.component.html',
  styleUrls: ['./cost-calculator.component.css']
})
export class CostCalculatorComponent {
  request = {
    numberOfPeople: 1,
    numberOfDays: 3,
    destinationId: null,
    hotelPricePerNight: 0,
    transportationCost: 0,
    foodCostPerDay: 0,
    activitiesCost: 0,
    otherCosts: 0
  };
  result: any = null;
  loading = false;

  constructor(private api: ApiService, private toastr: ToastrService) {}

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

  reset(): void {
    this.result = null;
    this.request = { numberOfPeople: 1, numberOfDays: 3, destinationId: null, hotelPricePerNight: 0, transportationCost: 0, foodCostPerDay: 0, activitiesCost: 0, otherCosts: 0 };
  }
}
