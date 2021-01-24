import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '@environments/environment';
import { HttpClient } from '@angular/common/http';

import { IUser } from '@core/models';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(
    private http: HttpClient
  ) { }

  public getUsers(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/api/users`);
  }

  public getBlockedUsers(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/api/users/blocked`);
  }

  public update(id: number, block: boolean): Observable<IUser> {
    const description = block ? "Admin has blocked your account" : "";
    return this.http.put<IUser>(`${environment.apiUrl}/api/users/${id}`, { blocked: block, description: description});
  }

  public editAccount(data:any) {
    return this.http.put(`${environment.apiUrl}/api/auth/my`, data);
  }
}
