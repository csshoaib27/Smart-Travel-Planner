import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TripService } from '../../services/trip.service';
import { Trip } from '../../models/trip.model';

@Component({
    selector: 'app-trip-list',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './trip-list.component.html',
    styleUrl: './trip-list.component.css'
})
export class TripListComponent implements OnInit {
    trips: Trip[] = [];
    loading = true;

    constructor(private tripService: TripService) { }

    ngOnInit() {
        this.loadTrips();
    }

    loadTrips() {
        this.loading = true;
        this.tripService.getAllTrips().subscribe(
            (data: Trip[]) => {
                this.trips = data;
                this.loading = false;
            },
            (error) => {
                console.error('Error loading trips:', error);
                this.loading = false;
            }
        );
    }

    deleteTrip(id: string) {
        if (confirm('Are you sure you want to delete this trip?')) {
            this.tripService.deleteTrip(id).subscribe(
                () => {
                    this.trips = this.trips.filter(t => t.id !== id);
                },
                (error) => {
                    console.error('Error deleting trip:', error);
                }
            );
        }
    }

    viewDetails(tripTitle: string) {
        console.log('Viewing details for trip:', tripTitle);
        alert(`View details for trip: ${tripTitle}`);
    }
}
