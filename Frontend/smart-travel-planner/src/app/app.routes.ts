import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent)
    },
    // TODO: Implement remaining components below
    // {
    //   path: 'destinations',
    //   loadComponent: () => import('./components/destination/destination-list/destination-list.component').then(m => m.DestinationListComponent)
    // },
    // {
    //   path: 'destinations/:id',
    //   loadComponent: () => import('./components/destination/destination-detail/destination-detail.component').then(m => m.DestinationDetailComponent)
    // },
    // {
    //   path: 'hotels/:destinationId',
    //   loadComponent: () => import('./components/hotels/hotel-list/hotel-list.component').then(m => m.HotelListComponent)
    // },
    // {
    //   path: 'trip-planner',
    //   loadComponent: () => import('./components/trip-planner/trip-planner.component').then(m => m.TripPlannerComponent)
    // },
    // {
    //   path: 'trips',
    //   loadComponent: () => import('./components/trip-planner/trip-list/trip-list.component').then(m => m.TripListComponent)
    // },
    // {
    //   path: 'trips/:id',
    //   loadComponent: () => import('./components/trip-planner/trip-detail/trip-detail.component').then(m => m.TripDetailComponent)
    // },
    // {
    //   path: 'reviews/:destinationId',
    //   loadComponent: () => import('./components/reviews/review-list/review-list.component').then(m => m.ReviewListComponent)
    // },
    // {
    //   path: 'safety/:destinationId',
    //   loadComponent: () => import('./components/safety-alerts/safety-detail/safety-detail.component').then(m => m.SafetyDetailComponent)
    // },
    // {
    //   path: 'food/:destinationId',
    //   loadComponent: () => import('./components/food/food-list/food-list.component').then(m => m.FoodListComponent)
    // },
    // {
    //   path: 'chatbot',
    //   loadComponent: () => import('./components/chatbot/chatbot.component').then(m => m.ChatbotComponent)
    // }
];
