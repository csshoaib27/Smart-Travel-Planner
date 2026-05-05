import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private api: ApiService) {
    this.currentUserSubject = new BehaviorSubject<any>(this.getUserFromLocalStorage());
    this.currentUser = this.currentUserSubject.asObservable();
  }

  get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  register(username: string, email: string, password: string, fullName: string, phone: string): Observable<any> {
    return this.api.register({ username, email, password, fullName, phone });
  }

  login(username: string, password: string): Observable<any> {
    return this.api.login({ username, password }).pipe(
      map(response => {
        if (response?.data) {
          this.saveUser(response.data);
        }
        return response;
      })
    );
  }

  logout(): void {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    return !!this.currentUserValue?.token;
  }

  isAdmin(): boolean {
    return this.currentUserValue?.isAdmin === true;
  }

  getToken(): string {
    return this.currentUserValue?.token ?? '';
  }

  getUserId(): number {
    return this.currentUserValue?.userId ?? 0;
  }

  getUsername(): string {
    return this.currentUserValue?.username ?? '';
  }

  private saveUser(user: any): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  private getUserFromLocalStorage(): any {
    try {
      const user = localStorage.getItem('currentUser');
      return user ? JSON.parse(user) : null;
    } catch {
      return null;
    }
  }
}
