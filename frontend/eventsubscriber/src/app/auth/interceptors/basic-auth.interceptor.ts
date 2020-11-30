import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  AuthService,
  StorageService 
 } from '../services';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {
    constructor(
      private StorageService: StorageService
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const currentBasicAuthToken = this.StorageService.get();
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