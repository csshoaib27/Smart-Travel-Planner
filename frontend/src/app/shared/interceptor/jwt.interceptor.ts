import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    try {
      const currentUser = localStorage.getItem('currentUser');
      if (currentUser) {
        const user = JSON.parse(currentUser);
        if (user?.token) {
          request = request.clone({
            setHeaders: { Authorization: `Bearer ${user.token}` }
          });
        }
      }
    } catch {
      // invalid JSON in localStorage — ignore
    }
    return next.handle(request);
  }
}
