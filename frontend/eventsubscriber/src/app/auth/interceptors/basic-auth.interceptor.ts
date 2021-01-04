import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  AuthService 
 } from '../services';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {
    constructor(
      private authService: AuthService
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const currentBasicAuthToken = this.authService.getToken();
        if (currentBasicAuthToken) {
            request = request.clone({
                setHeaders: { 
                    Authorization: `Basic ${currentBasicAuthToken}`
                }
            });
        }
        return next.handle(request);
    }
}