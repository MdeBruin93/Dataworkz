import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  public isAuthenticated(): boolean {
    // TODO: check if user is authenticated
    return false;
  }

  public login(email: string, password: string): Observable<any> {
    // TODO: add POST request to authenticate user
    return new Observable();
  }

  public logout(): void {
    // TODO: add logic to logout user 
  }
}
