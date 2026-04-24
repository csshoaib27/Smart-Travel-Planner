import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastrModule } from 'ngx-toastr';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Components
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './home/home.component';
import { DestinationSearchComponent } from './destinations/search/search.component';
import { DestinationDetailComponent } from './destinations/detail/detail.component';
import { HotelListComponent } from './hotels/list/list.component';
import { HotelDetailComponent } from './hotels/detail/detail.component';
import { CostCalculatorComponent } from './calculator/cost-calculator/cost-calculator.component';
import { ItineraryGeneratorComponent } from './itinerary/generator/generator.component';
import { ItineraryDetailComponent } from './itinerary/detail/detail.component';
import { AdminDashboardComponent } from './admin/dashboard/dashboard.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';

// Services
import { ApiService } from './services/api.service';
import { AuthService } from './services/auth.service';
import { JwtInterceptor } from './shared/interceptor/jwt.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    DestinationSearchComponent,
    DestinationDetailComponent,
    HotelListComponent,
    HotelDetailComponent,
    CostCalculatorComponent,
    ItineraryGeneratorComponent,
    ItineraryDetailComponent,
    AdminDashboardComponent,
    NavbarComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    ToastrModule.forRoot({
      timeOut: 4000,
      positionClass: 'toast-top-right',
      preventDuplicates: true
    })
  ],
  providers: [
    ApiService,
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
