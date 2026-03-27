import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Hotel } from '../models/hotel.model';

@Injectable({
    providedIn: 'root'
})
export class HotelService {
    private hotels: Hotel[] = [
        {
            id: 'hotel-001',
            name: 'Taj Beachfront Goa',
            destinationId: 'dest-001',
            city: 'Goa',
            address: 'Calangute Beach, Goa',
            pricePerNight: 5000,
            rating: 4.6,
            amenities: ['WiFi', 'Swimming Pool', 'Beach access', 'Restaurant', 'Spa'],
            image: 'https://via.placeholder.com/400x300?text=Taj+Goa',
            description: 'Luxury beachfront resort with modern amenities',
            roomTypes: ['Deluxe Room', 'Suite', 'Beach Villa'],
            contact: '+91-832-6456789',
            checkInTime: '2:00 PM',
            checkOutTime: '11:00 AM',
            wifi: true,
            parking: true,
            gym: true,
            restaurant: true
        },
        {
            id: 'hotel-002',
            name: 'Coconut Palms Goa',
            destinationId: 'dest-001',
            city: 'Goa',
            address: 'Baga Beach, Goa',
            pricePerNight: 2500,
            rating: 4.2,
            amenities: ['WiFi', 'Rooftop Bar', 'Beach access', 'Cafe'],
            image: 'https://via.placeholder.com/400x300?text=Coconut+Palms',
            description: 'Budget-friendly beachside hotel with vibrant atmosphere',
            roomTypes: ['Standard Room', 'Deluxe Room'],
            contact: '+91-832-2234567',
            checkInTime: '1:00 PM',
            checkOutTime: '12:00 PM',
            wifi: true,
            parking: false,
            gym: false,
            restaurant: true
        },
        {
            id: 'hotel-003',
            name: 'The Himalayan Resort Manali',
            destinationId: 'dest-002',
            city: 'Manali',
            address: 'Aleo Road, Manali',
            pricePerNight: 4000,
            rating: 4.7,
            amenities: ['WiFi', 'Mountain View', 'Bonfire area', 'Restaurant', 'Adventure desk'],
            image: 'https://via.placeholder.com/400x300?text=Himalayan+Resort',
            description: 'Mountain resort with adventure package coordination',
            roomTypes: ['Cottage', 'Deluxe Room', 'Family Room'],
            contact: '+91-1902-258000',
            checkInTime: '12:00 PM',
            checkOutTime: '11:00 AM',
            wifi: true,
            parking: true,
            gym: true,
            restaurant: true
        },
        {
            id: 'hotel-004',
            name: 'Budget Stay Manali',
            destinationId: 'dest-002',
            city: 'Manali',
            address: 'Main Street, Manali',
            pricePerNight: 1200,
            rating: 3.8,
            amenities: ['WiFi', 'Common Kitchen', 'Shared bathroom'],
            image: 'https://via.placeholder.com/400x300?text=Budget+Stay',
            description: 'Economical stay for backpackers',
            roomTypes: ['Dormitory', 'Private Room'],
            contact: '+91-1902-255555',
            checkInTime: '2:00 PM',
            checkOutTime: '10:00 AM',
            wifi: true,
            parking: false,
            gym: false,
            restaurant: false
        }
    ];

    constructor() { }

    getHotelsByDestination(destinationId: string): Observable<Hotel[]> {
        return of(this.hotels.filter(h => h.destinationId === destinationId));
    }

    getHotelById(id: string): Observable<Hotel | undefined> {
        return of(this.hotels.find(h => h.id === id));
    }

    searchHotels(filters: any): Observable<Hotel[]> {
        let results = [...this.hotels];

        if (filters.destinationId) {
            results = results.filter(h => h.destinationId === filters.destinationId);
        }

        if (filters.minPrice && filters.maxPrice) {
            results = results.filter(h => h.pricePerNight >= filters.minPrice && h.pricePerNight <= filters.maxPrice);
        }

        if (filters.minRating) {
            results = results.filter(h => h.rating >= filters.minRating);
        }

        if (filters.amenities && filters.amenities.length > 0) {
            results = results.filter(h => filters.amenities.every((amenity: string) => h.amenities.includes(amenity)));
        }

        return of(results);
    }

    getTopHotels(): Observable<Hotel[]> {
        return of([...this.hotels].sort((a, b) => b.rating - a.rating).slice(0, 5));
    }
}
