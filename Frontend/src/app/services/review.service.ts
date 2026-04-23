import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Review } from '../models/review.model';

@Injectable({
    providedIn: 'root'
})
export class ReviewService {
    private reviews: Review[] = [
        {
            id: 'rev-001',
            userId: 'user-1',
            userName: 'Rahul Singh',
            destinationId: 'dest-001',
            rating: 4.5,
            title: 'Amazing beach experience',
            comment: 'Goa was perfect for a family trip. Great beaches and friendly people.',
            date: new Date('2024-02-15'),
            helpful: 42,
            images: [],
            tripType: 'family'
        },
        {
            id: 'rev-002',
            userId: 'user-2',
            userName: 'Priya Sharma',
            destinationId: 'dest-002',
            rating: 5,
            title: 'Adventure paradise',
            comment: 'Manali is incredible for adventure lovers. Paragliding was unforgettable!',
            date: new Date('2024-02-10'),
            helpful: 65,
            images: [],
            tripType: 'adventure'
        }
    ];

    constructor() { }

    getReviewsByDestination(destinationId: string): Observable<Review[]> {
        return of(this.reviews.filter(r => r.destinationId === destinationId));
    }

    getReviewById(id: string): Observable<Review | undefined> {
        return of(this.reviews.find(r => r.id === id));
    }

    createReview(review: Review): Observable<Review> {
        review.id = 'rev-' + Math.random().toString(36).substr(2, 9);
        review.date = new Date();
        this.reviews.push(review);
        return of(review);
    }

    updateReview(review: Review): Observable<Review> {
        const index = this.reviews.findIndex(r => r.id === review.id);
        if (index !== -1) {
            this.reviews[index] = review;
        }
        return of(review);
    }

    deleteReview(id: string): Observable<void> {
        this.reviews = this.reviews.filter(r => r.id !== id);
        return of(undefined);
    }

    getAverageRating(destinationId: string): Observable<number> {
        const destReviews = this.reviews.filter(r => r.destinationId === destinationId);
        if (destReviews.length === 0) return of(0);
        const sum = destReviews.reduce((acc, r) => acc + r.rating, 0);
        return of(sum / destReviews.length);
    }

    getTopReviews(destinationId: string, limit: number = 5): Observable<Review[]> {
        return of(
            this.reviews
                .filter(r => r.destinationId === destinationId)
                .sort((a, b) => b.helpful - a.helpful)
                .slice(0, limit)
        );
    }

    markHelpful(reviewId: string): Observable<void> {
        const review = this.reviews.find(r => r.id === reviewId);
        if (review) {
            review.helpful++;
        }
        return of(undefined);
    }

    getReviewsByTripType(destinationId: string, tripType: string): Observable<Review[]> {
        return of(this.reviews.filter(r => r.destinationId === destinationId && r.tripType === tripType));
    }
}
