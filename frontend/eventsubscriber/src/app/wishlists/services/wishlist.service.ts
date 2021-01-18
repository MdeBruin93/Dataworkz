import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {

  constructor(
    private http: HttpClient,
  ) { }

  public findByUser(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.apiUrl}/api/wishlists/findbyuser`);
  }

  public update(id: number, formData: any): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/api/wishlists/${id}`, formData);
  }

  public create(formData: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/wishlists/`, formData);
  }

  public delete(id: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiUrl}/api/wishlists/${id}`);
  }
}
