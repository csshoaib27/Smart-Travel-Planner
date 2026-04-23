import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { DestinationService } from '../../services/destination.service';
import { Destination } from '../../models/destination.model';

@Component({
    selector: 'app-destination-list',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule],
    templateUrl: './destination-list.component.html',
    styleUrl: './destination-list.component.css'
})
export class DestinationListComponent implements OnInit {
    destinations: Destination[] = [];
    filteredDestinations: Destination[] = [];
    loading = true;
    selectedBudget = 'all';
    searchQuery = '';
    selectedInterests: string[] = [];
    minPrice = 0;
    maxPrice = 5000;
    minRating = 0;
    maxDistance = 5000;

    budgetOptions = ['all', 'low', 'medium', 'high'];

    constructor(
        private destinationService: DestinationService,
        private route: ActivatedRoute,
        private router: Router
    ) { }

    ngOnInit() {
        // Load destinations once
        this.loadDestinations();

        // Subscribe to query params changes for filtering
        this.route.queryParams.subscribe(params => {
            console.log('Query params received:', params);
            this.selectedBudget = params['budget'] || 'all';

            this.minPrice = params['minFare'] ? Number(params['minFare']) : 0;
            this.maxPrice = params['maxFare'] ? Number(params['maxFare']) : 5000;
            this.minRating = params['minRating'] ? Number(params['minRating']) : 0;
            this.maxDistance = params['maxDistance'] ? Number(params['maxDistance']) : 5000;

            // Handle interests - could be array or comma-separated string
            if (params['interests']) {
                if (Array.isArray(params['interests'])) {
                    this.selectedInterests = params['interests'];
                } else if (typeof params['interests'] === 'string') {
                    this.selectedInterests = params['interests'].split(',');
                } else {
                    this.selectedInterests = [];
                }
            } else {
                this.selectedInterests = [];
            }

            console.log('Parsed interests:', this.selectedInterests);
            this.applyFilters();
        });
    }

    loadDestinations() {
        this.loading = true;
        this.destinationService.getAllDestinations().subscribe(
            (data: Destination[]) => {
                this.destinations = data;
                this.applyFilters();
                this.loading = false;
            },
            (error) => {
                console.error('Error loading destinations:', error);
                this.loading = false;
            }
        );
    }

    applyFilters() {
        const query = this.searchQuery.toLowerCase().trim();
        this.filteredDestinations = this.destinations.filter(dest => {
            const matchesBudget = this.selectedBudget === 'all' || dest.budget === this.selectedBudget;
            const matchesSearch = query === '' ||
                dest.name.toLowerCase().includes(query) ||
                dest.city.toLowerCase().includes(query) ||
                dest.country.toLowerCase().includes(query) ||
                dest.region.toLowerCase().includes(query);
            const matchesInterests = this.selectedInterests.length === 0 ||
                this.selectedInterests.some(interest => dest.interests.includes(interest));
            const matchesPrice = dest.costPerDay >= this.minPrice && dest.costPerDay <= this.maxPrice;
            const matchesRating = dest.rating >= this.minRating;
            const matchesDistance = dest.distance <= this.maxDistance;
            return matchesBudget && matchesSearch && matchesInterests && matchesPrice && matchesRating && matchesDistance;
        });
    }

    onBudgetChange(budget: string) {
        this.selectedBudget = budget;
        this.applyFilters();
    }

    onSearchChange(query: string) {
        this.searchQuery = query;
        this.applyFilters();
    }

    viewDetails(destinationId: string) {
        this.router.navigate(['/destinations', destinationId]);
    }
}
