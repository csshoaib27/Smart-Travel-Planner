import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { DestinationSearchComponent } from './destinations/search/search.component';
import { DestinationDetailComponent } from './destinations/detail/detail.component';
import { HotelListComponent } from './hotels/list/list.component';
import { HotelDetailComponent } from './hotels/detail/detail.component';
import { CostCalculatorComponent } from './calculator/cost-calculator/cost-calculator.component';
import { ItineraryGeneratorComponent } from './itinerary/generator/generator.component';
import { ItineraryDetailComponent } from './itinerary/detail/detail.component';
import { AdminDashboardComponent } from './admin/dashboard/dashboard.component';
import { MyTripsComponent } from './my-trips/my-trips.component';
import { AuthGuard } from './shared/guards/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'destinations', component: DestinationSearchComponent },
  { path: 'destinations/:id', component: DestinationDetailComponent },
  { path: 'hotels', component: HotelListComponent },
  { path: 'hotels/:id', component: HotelDetailComponent },
  { path: 'calculator', component: CostCalculatorComponent, canActivate: [AuthGuard] },
  { path: 'itineraries/new', component: ItineraryGeneratorComponent, canActivate: [AuthGuard] },
  { path: 'itineraries/:id', component: ItineraryDetailComponent, canActivate: [AuthGuard] },
  { path: 'my-trips', component: MyTripsComponent, canActivate: [AuthGuard] },
  { path: 'admin', component: AdminDashboardComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
