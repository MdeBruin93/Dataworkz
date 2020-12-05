import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import {
  AuthService,
  StorageService
} from '../services';

@Injectable()
export class ErrorHandlingInterceptor implements HttpInterceptor {
    constructor(
      private authService: AuthService,
      private storageService: StorageService
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status === 401) {
                this.authService.logout();
                this.storageService.remove();
            }

            const error = err.error.message || err.statusText;
            return throwError(error);
        }))
    }
}