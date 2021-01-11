import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment';
import { IUser } from '@core/models';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  public isAuthenticated(): boolean {
    return this.getToken() != "";
  }

  public register(data: object): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/auth/register`, data);
  }

  public login(email: string, password: string): Observable<any> {
    this.setToken(email, password);
    return this.http.get(`${environment.apiUrl}/api/auth/my`);
  }

  public logout(): void {
    localStorage.clear();
  }

  public forgotPassword(data: object): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/auth/forgot-password`, data);
  }

  public resetPassword(data: object): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/auth/reset-password`, data);
  }

  public activateAccount(data: object): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/auth/activate`, data);
  }

  public getToken(): string {
    let token = localStorage.getItem('auth.token');
    return token ? token.replace('"',"").replace('"',"") : "";
  }

  public setToken(email: string, password: string): void {
    localStorage.setItem('auth.token', this.encodeBasicAuthCredentials(email, password));
  }

  public getCurrentUser(): IUser | null {
    const user = localStorage.getItem('auth.currentUser');
    if (user) {
      return JSON.parse(user) as IUser;
    }
    return null;
  }

  public isAdmin(): boolean {
    const user = this.getCurrentUser();
    console.log(user);
    if (!user) {
      return false;
    }
    return user.role === "ROLE_ADMIN";
  }

  private encodeBasicAuthCredentials(email: string, password: string) {
    return window.btoa(email + ':' + password);
  }
}
