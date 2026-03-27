import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TripService } from '../../services/trip.service';
import { Trip } from '../../models/trip.model';

@Component({
    selector: 'app-trip-planner',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule],
    templateUrl: './trip-planner.component.html',
    styleUrl: './trip-planner.component.css'
})
export class TripPlannerComponent implements OnInit {
    tripForm = {
        destination: '',
        startDate: '',
        endDate: '',
        travelers: 1,
        budget: 'standard',
        interests: [] as string[]
    };

    interestOptions = [
        'Adventure', 'Beach', 'Culture', 'Food', 'Nature',
        'History', 'Shopping', 'Nightlife', 'Photography', 'Relaxation'
    ];

    budgetOptions = [
        { value: 'budget', label: 'Budget (₹5,000-20,000)' },
        { value: 'standard', label: 'Standard (₹20,000-50,000)' },
        { value: 'luxury', label: 'Luxury (₹50,000+)' }
    ];

    estimatedCost = 0;
    tripCreated = false;

    constructor(private tripService: TripService) { }

    ngOnInit() {
        console.log('Trip Planner Component initialized');
    }

    toggleInterest(interest: string) {
        const index = this.tripForm.interests.indexOf(interest);
        if (index > -1) {
            this.tripForm.interests.splice(index, 1);
        } else {
            this.tripForm.interests.push(interest);
        }
    }

    calculateCost() {
        const days = this.getDaysDifference();
        const budgetMultiplier = {
            'budget': 10000,
            'standard': 30000,
            'luxury': 60000
        }[this.tripForm.budget] || 30000;

        this.estimatedCost = days * this.tripForm.travelers * budgetMultiplier;
    }

    getDaysDifference(): number {
        if (this.tripForm.startDate && this.tripForm.endDate) {
            const start = new Date(this.tripForm.startDate);
            const end = new Date(this.tripForm.endDate);
            const diffTime = Math.abs(end.getTime() - start.getTime());
            return Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
        }
        return 1;
    }

    createTrip() {
        if (!this.tripForm.destination || !this.tripForm.startDate || !this.tripForm.endDate) {
            alert('Please fill in all required fields');
            return;
        }

        const newTrip: Trip = {
            id: String(Date.now()),
            userId: '',
            title: `Trip to ${this.tripForm.destination}`,
            description: '',
            destinationId: '',
            startDate: new Date(this.tripForm.startDate),
            endDate: new Date(this.tripForm.endDate),
            numberOfDays: this.getDaysDifference(),
            budget: this.estimatedCost,
            packageMode: 'family',
            itinerary: [],
            hotels: [],
            totalCost: this.estimatedCost,
            costBreakdown: {
                transportation: 0,
                accommodation: 0,
                food: 0,
                activities: 0,
                miscellaneous: 0
            },
            participants: this.tripForm.travelers,
            createdAt: new Date(),
            sharedWith: []
        };

        this.tripService.createTrip(newTrip).subscribe(
            (response) => {
                console.log('Trip created:', response);
                this.tripCreated = true;
                alert(`Trip to ${this.tripForm.destination} created successfully!`);
                setTimeout(() => this.resetForm(), 2000);
            },
            (error) => {
                console.error('Error creating trip:', error);
                alert('Error creating trip. Please try again.');
            }
        );
    }

    resetForm() {
        this.tripForm = {
            destination: '',
            startDate: '',
            endDate: '',
            travelers: 1,
            budget: 'standard',
            interests: []
        };
        this.estimatedCost = 0;
        this.tripCreated = false;
    }
}
