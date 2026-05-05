import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  // ==================== AUTH ENDPOINTS ====================
  register(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/register`, userData);
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, credentials);
  }

  resetPassword(username: string, email: string, newPassword: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/reset-password`, { username, email, newPassword });
  }

  validateToken(token: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.baseUrl}/auth/validate-token`, { headers });
  }

  // ==================== DESTINATION ENDPOINTS ====================
  getAllDestinations(): Observable<any> {
    return this.http.get(`${this.baseUrl}/destinations`);
  }

  getDestinationById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/destinations/${id}`);
  }

  searchDestinations(country?: string, travelType?: string, budgetCategory?: string, city?: string): Observable<any> {
    let url = `${this.baseUrl}/destinations/search?`;
    if (country) url += `country=${encodeURIComponent(country)}&`;
    if (travelType) url += `travelType=${encodeURIComponent(travelType)}&`;
    if (budgetCategory) url += `budgetCategory=${encodeURIComponent(budgetCategory)}&`;
    if (city) url += `city=${encodeURIComponent(city)}&`;
    return this.http.get(url);
  }

  getDestinationCountries(): Observable<any> {
    return this.http.get(`${this.baseUrl}/destinations/countries`);
  }

  getTopRatedDestinations(): Observable<any> {
    return this.http.get(`${this.baseUrl}/destinations/top-rated`);
  }

  getCitiesForCountry(country: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/destinations/cities?country=${encodeURIComponent(country)}`);
  }

  createDestination(destination: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/destinations`, destination);
  }

  // ==================== HOTEL ENDPOINTS ====================
  getAllHotels(): Observable<any> {
    return this.http.get(`${this.baseUrl}/hotels`);
  }

  getHotelById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/hotels/${id}`);
  }

  getHotelsByDestination(destinationId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/hotels/destination/${destinationId}`);
  }

  searchHotels(destinationId: number, minPrice?: number, maxPrice?: number): Observable<any> {
    let url = `${this.baseUrl}/hotels/search?destinationId=${destinationId}`;
    if (minPrice) url += `&minPrice=${minPrice}`;
    if (maxPrice) url += `&maxPrice=${maxPrice}`;
    return this.http.get(url);
  }

  createHotel(hotel: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/hotels`, hotel);
  }

  // ==================== COST CALCULATOR ENDPOINTS ====================
  calculateCost(request: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/calculator/calculate`, request);
  }

  calculateCostPerPerson(request: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/calculator/calculate-per-person`, request);
  }

  downloadItineraryPdf(request: any): Observable<Blob> {
    return this.http.post(`${this.baseUrl}/calculator/download-pdf`, request, {
      responseType: 'blob'
    });
  }

  downloadItineraryPlanPdf(request: any): Observable<Blob> {
    return this.http.post(`${this.baseUrl}/itineraries/plan-pdf`, request, {
      responseType: 'blob'
    });
  }

  searchGoogleHotels(destination: string, budgetTier: string, destinationId?: number | null): Observable<any> {
    let url = `${this.baseUrl}/hotels/google-hotels?destination=${encodeURIComponent(destination)}&budgetTier=${budgetTier}`;
    if (destinationId) url += `&destinationId=${destinationId}`;
    return this.http.get(url);
  }

  // ==================== ITINERARY ENDPOINTS ====================
  createItinerary(itinerary: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/itineraries`, itinerary);
  }

  getItineraryById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/itineraries/${id}`);
  }

  getUserItineraries(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/itineraries/user/${userId}`);
  }

  updateItinerary(id: number, itinerary: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/itineraries/${id}`, itinerary);
  }

  deleteItinerary(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/itineraries/${id}`);
  }

  // ==================== BOOKING ENDPOINTS ====================
  createBooking(booking: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/bookings`, booking);
  }

  getBookingById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/bookings/${id}`);
  }

  getUserBookings(userId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/bookings/user/${userId}`);
  }

  updateBooking(id: number, booking: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/bookings/${id}`, booking);
  }

  cancelBooking(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/bookings/${id}/cancel`, {});
  }

  // ==================== REVIEW ENDPOINTS ====================
  createReview(review: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/reviews`, review);
  }

  getReviewsByDestination(destinationId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/reviews/destination/${destinationId}`);
  }

  getReviewsByHotel(hotelId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/reviews/hotel/${hotelId}`);
  }

  updateReview(id: number, review: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/reviews/${id}`, review);
  }

  deleteReview(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/reviews/${id}`);
  }

  // ==================== ADMIN ENDPOINTS ====================
  getAllUsers(): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/users`);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/users/${id}`);
  }

  getAllBookings(): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/bookings`);
  }

  getSystemStats(): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/stats`);
  }

  getAllItineraries(): Observable<any> {
    return this.http.get(`${this.baseUrl}/admin/itineraries`);
  }

  deleteAdminItinerary(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/admin/itineraries/${id}`);
  }
}
