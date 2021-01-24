import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Store } from '@ngxs/store';
import { Logout } from '@core/store';

@Injectable()
export class ErrorHandlingInterceptor implements HttpInterceptor {
    constructor(
      private store: Store
    ) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError((err: HttpErrorResponse) => {
            if (!(err instanceof HttpErrorResponse)) {
              return next.handle(request);
            }
            if (err.status === 401) {
                this.store.dispatch(new Logout());
            }
            return throwError(err);
        }))
    }
}
