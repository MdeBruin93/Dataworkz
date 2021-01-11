import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

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

  public save(category: Category): Observable<Category> {
    //temporary
    category.color = 'ffffff';
    if (category.id != null) {
      return this.http.put<Category>(`${environment.apiUrl}/api/categories/${category.id}`, category);
    }
    return this.http.post<Category>(`${environment.apiUrl}/api/categories`, category);;
  }

  public delete(id: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiUrl}/api/categories/${id}`, {});
  }
}
