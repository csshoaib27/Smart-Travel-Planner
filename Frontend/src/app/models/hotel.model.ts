export interface Hotel {
    id: string;
    name: string;
    destinationId: string;
    city: string;
    address: string;
    pricePerNight: number;
    rating: number; // 0-5
    amenities: string[];
    image: string;
    description: string;
    roomTypes: string[];
    contact: string;
    checkInTime: string;
    checkOutTime: string;
    wifi: boolean;
    parking: boolean;
    gym: boolean;
    restaurant: boolean;
}
