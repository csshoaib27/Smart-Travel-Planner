import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Destination } from '../models/destination.model';

@Injectable({
    providedIn: 'root'
})
export class DestinationService {
    private destinations: Destination[] = [
        {
            id: 'dest-001',
            name: 'Goa',
            description: 'Tropical beaches and vibrant nightlife in South India',
            country: 'India',
            city: 'Panaji',
            region: 'South India',
            latitude: 15.3,
            longitude: 73.85,
            budget: 'low',
            temperature: 28,
            bestTimeToVisit: ['November', 'December', 'January', 'February'],
            distance: 1200,
            rating: 4.5,
            reviewCount: 1250,
            interests: ['beach', 'adventure', 'nightlife', 'budget'],
            image: 'https://via.placeholder.com/400x300?text=Goa',
            travelTime: '2-3 hours flight from Delhi',
            costPerDay: 1500,
            activities: ['Beach hopping', 'Water sports', 'Nightclubs', 'Beach shacks', 'Trek to Dudhsagar Waterfall'],
            safetyRating: 8
        },
        {
            id: 'dest-002',
            name: 'Manali',
            description: 'Mountain paradise with adventure sports and scenic beauty',
            country: 'India',
            city: 'Manali',
            region: 'Himachal Pradesh',
            latitude: 32.24,
            longitude: 77.19,
            budget: 'medium',
            temperature: 15,
            bestTimeToVisit: ['March', 'April', 'September', 'October'],
            distance: 1500,
            rating: 4.8,
            reviewCount: 980,
            interests: ['adventure', 'nature', 'hiking', 'budget'],
            image: 'https://via.placeholder.com/400x300?text=Manali',
            travelTime: '1.5 hours flight from Delhi',
            costPerDay: 2000,
            activities: ['Paragliding', 'Rock climbing', 'Trekking', 'River rafting', 'Mountain biking'],
            safetyRating: 9
        },
        {
            id: 'dest-003',
            name: 'Kerala',
            description: 'Serene backwaters and tropical beaches in South India',
            country: 'India',
            city: 'Kochi',
            region: 'South India',
            latitude: 9.97,
            longitude: 76.29,
            budget: 'medium',
            temperature: 26,
            bestTimeToVisit: ['June', 'July', 'August', 'September'],
            distance: 2000,
            rating: 4.7,
            reviewCount: 1500,
            interests: ['nature', 'relaxation', 'culture', 'budget'],
            image: 'https://via.placeholder.com/400x300?text=Kerala',
            travelTime: '2.5 hours flight from Delhi',
            costPerDay: 2200,
            activities: ['Houseboat backwaters', 'Beach walks', 'Spice plantation tours', 'Ayurveda', 'Fishing'],
            safetyRating: 9
        },
        {
            id: 'dest-004',
            name: 'Jaipur',
            description: 'The Pink City with historic forts and cultural heritage',
            country: 'India',
            city: 'Jaipur',
            region: 'Rajasthan',
            latitude: 26.91,
            longitude: 75.78,
            budget: 'low',
            temperature: 35,
            bestTimeToVisit: ['November', 'December', 'January', 'February'],
            distance: 260,
            rating: 4.4,
            reviewCount: 2100,
            interests: ['culture', 'history', 'budget', 'photography'],
            image: 'https://via.placeholder.com/400x300?text=Jaipur',
            travelTime: '4 hours drive from Delhi',
            costPerDay: 1800,
            activities: ['City Palace visit', 'Hawa Mahal tour', 'Jantar Mantar', 'Market shopping', 'Local cuisine'],
            safetyRating: 8
        },
        {
            id: 'dest-005',
            name: 'Ladakh',
            description: 'High altitude desert with stunning landscapes and adventure',
            country: 'India',
            city: 'Leh',
            region: 'North India',
            latitude: 34.16,
            longitude: 77.58,
            budget: 'high',
            temperature: 5,
            bestTimeToVisit: ['June', 'July', 'August', 'September'],
            distance: 1200,
            rating: 4.9,
            reviewCount: 650,
            interests: ['adventure', 'nature', 'photography', 'spiritual'],
            image: 'https://via.placeholder.com/400x300?text=Ladakh',
            travelTime: '1 hour flight from Delhi',
            costPerDay: 3500,
            activities: ['Trekking', 'Motorbike tours', 'Monasteries', 'Lakes', 'High altitude camping'],
            safetyRating: 7
        }
    ];

    constructor() { }

    getAllDestinations(): Observable<Destination[]> {
        return of(this.destinations);
    }

    getDestinationById(id: string): Observable<Destination | undefined> {
        return of(this.destinations.find(d => d.id === id));
    }

    searchDestinations(filters: any): Observable<Destination[]> {
        let results = [...this.destinations];

        if (filters.budget) {
            results = results.filter(d => d.budget === filters.budget);
        }

        if (filters.minFare && filters.maxFare) {
            results = results.filter(d => d.costPerDay >= filters.minFare && d.costPerDay <= filters.maxFare);
        }

        if (filters.minRating) {
            results = results.filter(d => d.rating >= filters.minRating);
        }

        if (filters.interests && filters.interests.length > 0) {
            results = results.filter(d => filters.interests.some((interest: string) => d.interests.includes(interest)));
        }

        if (filters.temperature) {
            const tempRange = 5;
            results = results.filter(d => Math.abs(d.temperature - filters.temperature) <= tempRange);
        }

        return of(results);
    }

    getPopularDestinations(): Observable<Destination[]> {
        return of([...this.destinations].sort((a, b) => b.rating - a.rating).slice(0, 3));
    }

    getTopRatedDestinations(): Observable<Destination[]> {
        return of([...this.destinations].sort((a, b) => b.rating - a.rating));
    }
}
