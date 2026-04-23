export interface PackageMode {
    type: 'family' | 'solo' | 'couple' | 'adventure';
    description: string;
    suggestedBudget: number;
    duration: string;
    activities: string[];
    accommodation: string;
    mealOptions: string[];
}
