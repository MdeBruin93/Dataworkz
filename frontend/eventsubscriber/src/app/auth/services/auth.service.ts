import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment';

import { StorageService } from './storage.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router
  ) { }

  public isAuthenticated(): boolean {
    return this.storageService.get() != null;
  }

  public register(data: object): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/auth/register`, data);
  }

  public login(email: string, password: string): Observable<any> {
    this.storageService.setToken(email, password);
    return this.http.get(`${environment.apiUrl}/api/auth/my`);
  }

  public logout(): void {
    this.storageService.remove();
    this.router.navigate(['./login']);
  }

  public forgotPassword(data: object): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/auth/forgot-password`, data);
  }

  public resetPassword(data: object): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/auth/reset-password`, data);
  }
}
