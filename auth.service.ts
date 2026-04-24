import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(this.getUserFromLocalStorage());
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  register(username: string, email: string, password: string, fullName: string, phone: string): Observable<any> {
    return this.http.post(`http://localhost:8080/api/auth/register`, {
      username,
      email,
      password,
      fullName,
      phone
    }).pipe(map(response => {
      if (response && response.data) {
        this.saveUserToLocalStorage(response.data);
        this.currentUserSubject.next(response.data);
      }
      return response;
    }));
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`http://localhost:8080/api/auth/login`, {
      username,
      password
    }).pipe(map(response => {
      if (response && response.data) {
        this.saveUserToLocalStorage(response.data);
        this.currentUserSubject.next(response.data);
      }
      return response;
    }));
  }

  logout(): void {
    this.removeUserFromLocalStorage();
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    return this.currentUserValue !== null && this.currentUserValue.token !== undefined;
  }

  isAdmin(): boolean {
    return this.currentUserValue && this.currentUserValue.isAdmin === true;
  }

  getToken(): string {
    if (this.currentUserValue) {
      return this.currentUserValue.token;
    }
    return '';
  }

  getUserId(): number {
    if (this.currentUserValue) {
      return this.currentUserValue.userId;
    }
    return 0;
  }

  getUsername(): string {
    if (this.currentUserValue) {
      return this.currentUserValue.username;
    }
    return '';
  }

  private saveUserToLocalStorage(user: any): void {
    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  private removeUserFromLocalStorage(): void {
    localStorage.removeItem('currentUser');
  }

  private getUserFromLocalStorage(): any {
    try {
      const user = localStorage.getItem('currentUser');
      return user ? JSON.parse(user) : null;
    } catch (error) {
      return null;
    }
  }
}
