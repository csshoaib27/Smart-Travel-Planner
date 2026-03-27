export interface SafetyAlert {
    id: string;
    destinationId: string;
    city: string;
    alertType: 'hospital' | 'police' | 'emergency' | 'tip';
    name: string;
    description: string;
    address: string;
    phone: string;
    latitude: number;
    longitude: number;
    distance: number; // in km
    operatingHours: string;
    services: string[];
}

export interface TravelTip {
    id: string;
    destinationId: string;
    category: string; // weather, culture, safety, entry, etc.
    title: string;
    description: string;
    essentials: string[];
    season: string;
}
