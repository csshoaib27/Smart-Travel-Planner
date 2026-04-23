import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DestinationService } from '../../services/destination.service';
import { Destination } from '../../models/destination.model';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [CommonModule, RouterModule, FormsModule],
    templateUrl: './home.component.html',
    styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
    popularDestinations: Destination[] = [];
    budget: string = 'low';
    interests: string[] = [];
    minFare: number = 0;
    maxFare: number = 5000;
    selectedInterests: string[] = [];

    interestOptions = [
        'beach', 'adventure', 'nature', 'culture', 'history',
        'nightlife', 'budget', 'relaxation', 'photography', 'spiritual'
    ];

    packageModes = [
        { type: 'family', icon: '👨‍👩‍👧‍👦', description: 'Family Package' },
        { type: 'solo', icon: '🚶', description: 'Solo Travel' },
        { type: 'couple', icon: '👫', description: 'Couple Package' },
        { type: 'adventure', icon: '🧗', description: 'Adventure Package' }
    ];

    constructor(private destinationService: DestinationService, private router: Router) { }

    ngOnInit() {
        this.loadPopularDestinations();
    }

    loadPopularDestinations() {
        this.destinationService.getPopularDestinations().subscribe(data => {
            this.popularDestinations = data;
        });
    }

    toggleInterest(interest: string) {
        if (this.selectedInterests.includes(interest)) {
            this.selectedInterests = this.selectedInterests.filter(i => i !== interest);
        } else {
            this.selectedInterests.push(interest);
        }
    }

    searchDestinations() {
        const filters = {
            budget: this.budget,
            interests: this.selectedInterests,
            minFare: this.minFare,
            maxFare: this.maxFare
        };
        console.log('Searching destinations with filters:', filters);
        console.log('Navigating to destinations page...');
        this.router.navigate(['/destinations'], { queryParams: filters });
    }

    startPlanningTrip() {
        console.log('Starting trip planning...');
        console.log('Navigating to trip planner...');
        this.router.navigate(['/trip-planner']);
    }

    getSuggestedTrips() {
        console.log('Getting suggested trips based on budget and interests');
        console.log('Navigating to my trips...');
        this.router.navigate(['/trips']);
    }

    selectPackageAndPlan(packageType: string) {
        console.log('Selected package type:', packageType);
        console.log('Navigating to trip planner...');
        this.router.navigate(['/trip-planner'], { queryParams: { mode: packageType } });
    }
}
