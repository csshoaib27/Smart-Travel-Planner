export interface Trip {
    id: string;
    userId: string;
    title: string;
    description: string;
    destinationId: string;
    startDate: Date;
    endDate: Date;
    numberOfDays: number;
    budget: number;
    packageMode: 'family' | 'solo' | 'couple' | 'adventure';
    itinerary: ItineraryDay[];
    hotels: string[]; // hotel ids
    totalCost: number;
    costBreakdown: CostBreakdown;
    participants: number;
    createdAt: Date;
    sharedWith: string[]; // user emails or IDs
}

export interface ItineraryDay {
    day: number;
    title: string;
    activities: Activity[];
    meals: Meal[];
    notes: string;
}

export interface Activity {
    id: string;
    name: string;
    description: string;
    time: string;
    cost: number;
    duration: string;
}

export interface Meal {
    id: string;
    name: string;
    type: 'breakfast' | 'lunch' | 'dinner';
    cuisineType: string;
    cost: number;
    traditional: boolean;
}

export interface CostBreakdown {
    transportation: number;
    accommodation: number;
    food: number;
    activities: number;
    miscellaneous: number;
}
