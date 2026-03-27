export interface Destination {
    id: string;
    name: string;
    description: string;
    country: string;
    city: string;
    region: string;
    latitude: number;
    longitude: number;
    budget: 'low' | 'medium' | 'high';
    temperature: number;
    bestTimeToVisit: string[];
    distance: number; // in km from user location
    rating: number; // 0-5
    reviewCount: number;
    interests: string[]; // nature, adventure, budget, cultural, etc.
    image: string;
    travelTime: string; // estimated travel time
    costPerDay: number;
    activities: string[];
    safetyRating: number; // 0-10
}
