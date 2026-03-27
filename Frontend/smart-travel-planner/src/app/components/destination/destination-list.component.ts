import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
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

    budgetOptions = ['all', 'budget', 'standard', 'luxury'];

    constructor(private destinationService: DestinationService, private route: ActivatedRoute) { }

    ngOnInit() {
        // Load destinations once
        this.loadDestinations();

        // Subscribe to query params changes for filtering
        this.route.queryParams.subscribe(params => {
            console.log('Query params received:', params);
            this.selectedBudget = params['budget'] || 'all';

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
        this.filteredDestinations = this.destinations.filter(dest => {
            const matchesBudget = this.selectedBudget === 'all' || dest.budget === this.selectedBudget;
            const matchesSearch = dest.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
                dest.city.toLowerCase().includes(this.searchQuery.toLowerCase());
            const matchesInterests = this.selectedInterests.length === 0 ||
                this.selectedInterests.some(interest => dest.interests.includes(interest));
            return matchesBudget && matchesSearch && matchesInterests;
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

    viewDetails(destinationName: string) {
        console.log('Viewing details for:', destinationName);
        alert(`View Details for ${destinationName}`);
    }
}
