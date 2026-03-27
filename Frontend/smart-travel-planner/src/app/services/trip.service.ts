import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Trip, CostBreakdown } from '../models/trip.model';

@Injectable({
    providedIn: 'root'
})
export class TripService {
    private trips: Trip[] = [];
    private searchHistory: any[] = [];

    constructor() {
        this.initializeSampleTrips();
    }

    private initializeSampleTrips() {
        this.trips = [
            {
                id: 'trip-001',
                userId: 'user-1',
                title: 'Goa Beach Holiday',
                description: 'Amazing beach vacation in Goa',
                destinationId: 'dest-001',
                startDate: new Date('2024-03-15'),
                endDate: new Date('2024-03-22'),
                numberOfDays: 7,
                budget: 35000,
                packageMode: 'family',
                itinerary: [],
                hotels: ['hotel-001'],
                totalCost: 45000,
                costBreakdown: {
                    transportation: 15000,
                    accommodation: 20000,
                    food: 5000,
                    activities: 3000,
                    miscellaneous: 2000
                },
                participants: 4,
                createdAt: new Date(),
                sharedWith: []
            }
        ];
    }

    getAllTrips(): Observable<Trip[]> {
        return of(this.trips);
    }

    getTripById(id: string): Observable<Trip | undefined> {
        return of(this.trips.find(t => t.id === id));
    }

    getUserTrips(userId: string): Observable<Trip[]> {
        return of(this.trips.filter(t => t.userId === userId));
    }

    createTrip(trip: Trip): Observable<Trip> {
        trip.id = 'trip-' + Math.random().toString(36).substr(2, 9);
        trip.createdAt = new Date();
        this.trips.push(trip);
        return of(trip);
    }

    updateTrip(trip: Trip): Observable<Trip> {
        const index = this.trips.findIndex(t => t.id === trip.id);
        if (index !== -1) {
            this.trips[index] = trip;
        }
        return of(trip);
    }

    deleteTrip(id: string): Observable<void> {
        this.trips = this.trips.filter(t => t.id !== id);
        return of(undefined);
    }

    calculateTripCost(
        hotelCost: number,
        numberOfDays: number,
        estimatedFoodCost: number,
        estimatedActivityCost: number,
        transportationCost: number
    ): CostBreakdown {
        return {
            accommodation: hotelCost * numberOfDays,
            food: estimatedFoodCost * numberOfDays,
            activities: estimatedActivityCost * numberOfDays,
            transportation: transportationCost,
            miscellaneous: (hotelCost * numberOfDays * 0.1) // 10% misc
        };
    }

    getSuggestedTrips(budget: number, interests: string[]): Observable<Trip[]> {
        // This would be more sophisticated on backend
        return of(this.trips.filter(t => t.budget <= budget));
    }

    shareTrip(tripId: string, emails: string[]): Observable<void> {
        const trip = this.trips.find(t => t.id === tripId);
        if (trip) {
            trip.sharedWith = [...new Set([...trip.sharedWith, ...emails])];
        }
        return of(undefined);
    }

    addToSearchHistory(search: any): void {
        this.searchHistory.push({
            ...search,
            timestamp: new Date()
        });
    }

    getSearchHistory(): Observable<any[]> {
        return of(this.searchHistory);
    }

    splitPayment(tripId: string, numberOfPeople: number): Observable<number> {
        const trip = this.trips.find(t => t.id === tripId);
        if (trip) {
            return of(trip.totalCost / numberOfPeople);
        }
        return of(0);
    }
}
