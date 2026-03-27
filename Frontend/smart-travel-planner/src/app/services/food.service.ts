import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { TraditionalFood } from '../models/food.model';

@Injectable({
    providedIn: 'root'
})
export class FoodService {
    private traditionalFoods: TraditionalFood[] = [
        {
            id: 'food-001',
            destinationId: 'dest-001',
            city: 'Goa',
            name: 'Fish Curry Rice',
            description: 'Spicy fish curry cooked in coconut milk, a Goan specialty',
            cuisine: 'Goan',
            bestPlacesToTry: ['Baga Beach shacks', 'Fort Aguada', 'Panjim restaurants'],
            averageCost: 300,
            dietaryOptions: ['Non-vegetarian'],
            image: 'https://via.placeholder.com/300x200?text=Fish+Curry',
            season: 'Year-round',
            ingredients: ['Fish', 'Coconut milk', 'Red chili', 'Turmeric', 'Tamarind']
        },
        {
            id: 'food-002',
            destinationId: 'dest-001',
            city: 'Goa',
            name: 'Prawn Vindaloo',
            description: 'Hot and spicy prawn curry with vinegar',
            cuisine: 'Goan',
            bestPlacesToTry: ['Traditional Goan restaurants', 'Local eateries'],
            averageCost: 400,
            dietaryOptions: ['Non-vegetarian'],
            image: 'https://via.placeholder.com/300x200?text=Prawn+Vindaloo',
            season: 'Year-round',
            ingredients: ['Prawns', 'Chili', 'Vinegar', 'Garlic', 'Coconut']
        },
        {
            id: 'food-003',
            destinationId: 'dest-002',
            city: 'Manali',
            name: 'Himachali Dham',
            description: 'Traditional multi-course vegetarian meal of Himachal Pradesh',
            cuisine: 'Himachali',
            bestPlacesToTry: ['Local dhabas', 'Village homestays', 'Traditional restaurants'],
            averageCost: 250,
            dietaryOptions: ['Vegetarian', 'Vegan'],
            image: 'https://via.placeholder.com/300x200?text=Himachali+Dham',
            season: 'Year-round',
            ingredients: ['Local vegetables', 'Chickpeas', 'Rice', 'Moong dal', 'Spices']
        },
        {
            id: 'food-004',
            destinationId: 'dest-002',
            city: 'Manali',
            name: 'Kulfi',
            description: 'Traditional frozen dessert',
            cuisine: 'Indian',
            bestPlacesToTry: ['Local markets', 'Dessert shops'],
            averageCost: 50,
            dietaryOptions: ['Vegetarian'],
            image: 'https://via.placeholder.com/300x200?text=Kulfi',
            season: 'Year-round',
            ingredients: ['Milk', 'Cardamom', 'Nuts', 'Dry fruits']
        },
        {
            id: 'food-005',
            destinationId: 'dest-003',
            city: 'Kerala',
            name: 'Appalachicurry',
            description: 'Rice cakes in savory yogurt curry, Kerala specialty',
            cuisine: 'Kerala',
            bestPlacesToTry: ['Local Kerala restaurants', 'Home-based eateries'],
            averageCost: 200,
            dietaryOptions: ['Vegetarian'],
            image: 'https://via.placeholder.com/300x200?text=Appalachicurry',
            season: 'Year-round',
            ingredients: ['Rice', 'Yogurt', 'Coconut', 'Turmeric']
        },
        {
            id: 'food-006',
            destinationId: 'dest-004',
            city: 'Jaipur',
            name: 'Gatte ki Sabzi',
            description: 'Gram flour dumplings in a spiced yogurt curry',
            cuisine: 'Rajasthani',
            bestPlacesToTry: ['Traditional Rajasthani restaurants', 'Local eateries'],
            averageCost: 150,
            dietaryOptions: ['Vegetarian'],
            image: 'https://via.placeholder.com/300x200?text=Gatte+ki+Sabzi',
            season: 'Year-round',
            ingredients: ['Gram flour', 'Yogurt', 'Green chili', 'Turmeric']
        }
    ];

    constructor() { }

    getFoodsByDestination(destinationId: string): Observable<TraditionalFood[]> {
        return of(this.traditionalFoods.filter(f => f.destinationId === destinationId));
    }

    getFoodById(id: string): Observable<TraditionalFood | undefined> {
        return of(this.traditionalFoods.find(f => f.id === id));
    }

    getFoodsByCuisine(cuisine: string): Observable<TraditionalFood[]> {
        return of(this.traditionalFoods.filter(f => f.cuisine === cuisine));
    }

    getVegetarianFoods(destinationId: string): Observable<TraditionalFood[]> {
        return of(
            this.traditionalFoods.filter(
                f => f.destinationId === destinationId && f.dietaryOptions.includes('Vegetarian')
            )
        );
    }

    getVeganFoods(destinationId: string): Observable<TraditionalFood[]> {
        return of(
            this.traditionalFoods.filter(f => f.destinationId === destinationId && f.dietaryOptions.includes('Vegan'))
        );
    }

    searchByBudget(maxCost: number): Observable<TraditionalFood[]> {
        return of(this.traditionalFoods.filter(f => f.averageCost <= maxCost));
    }

    addFood(food: TraditionalFood): Observable<TraditionalFood> {
        food.id = 'food-' + Math.random().toString(36).substr(2, 9);
        this.traditionalFoods.push(food);
        return of(food);
    }
}
