import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';

import { Category } from '@core/models';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {

  constructor(
    private http: HttpClient
  ) { }

  public getAll(): Observable<Category[]> {
    return this.http.get<Category[]>(`${environment.apiUrl}/api/categories/`);
  }

  public findById(id: number): Observable<Category> {
    return this.http.get<Category>(`${environment.apiUrl}/api/categories/${id}`);
  }
}
