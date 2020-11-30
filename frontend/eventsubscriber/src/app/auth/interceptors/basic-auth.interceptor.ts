import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  StorageService 
 } from '../services';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {
    constructor(
      private storageService: StorageService
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const currentBasicAuthToken = this.storageService.get();
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