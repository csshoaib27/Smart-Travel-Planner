export interface Review {
    id: string;
    userId: string;
    userName: string;
    destinationId: string;
    rating: number; // 0-5
    title: string;
    comment: string;
    date: Date;
    helpful: number;
    images: string[];
    tripType: 'family' | 'solo' | 'couple' | 'adventure';
}
