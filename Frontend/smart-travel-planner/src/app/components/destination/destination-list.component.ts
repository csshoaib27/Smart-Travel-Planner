import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
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

    budgetOptions = ['all', 'budget', 'standard', 'luxury'];

    constructor(private destinationService: DestinationService) { }

    ngOnInit() {
        this.loadDestinations();
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
            return matchesBudget && matchesSearch;
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
