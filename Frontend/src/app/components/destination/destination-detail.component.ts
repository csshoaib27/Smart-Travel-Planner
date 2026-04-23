import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { DestinationService } from '../../services/destination.service';
import { Destination } from '../../models/destination.model';

@Component({
    selector: 'app-destination-detail',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './destination-detail.component.html',
    styleUrl: './destination-detail.component.css'
})
export class DestinationDetailComponent implements OnInit {
    destination: Destination | null = null;
    loading = true;

    constructor(
        private route: ActivatedRoute,
        private destinationService: DestinationService
    ) { }

    ngOnInit() {
        this.route.params.subscribe(params => {
            const id = params['id'];
            this.loadDestinationDetail(id);
        });
    }

    loadDestinationDetail(id: string) {
        this.loading = true;
        this.destinationService.getDestinationById(id).subscribe(
            (data: Destination | undefined) => {
                if (data) {
                    this.destination = data;
                } else {
                    console.error('Destination not found');
                }
                this.loading = false;
            },
            (error) => {
                console.error('Error loading destination:', error);
                this.loading = false;
            }
        );
    }

    bookTrip() {
        if (this.destination) {
            alert(`Booking trip to ${this.destination.name}! This feature will be implemented soon.`);
            console.log('Booking trip to:', this.destination.name);
        }
    }

    addToWishlist() {
        if (this.destination) {
            alert(`${this.destination.name} added to wishlist!`);
            console.log('Added to wishlist:', this.destination.name);
        }
    }
}
