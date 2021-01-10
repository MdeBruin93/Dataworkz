import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QaService {

  constructor(
    private http: HttpClient,
  ) { }

  public create(data: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/questions/`, data);
  }

  public delete(id: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiUrl}/api/questions/${id}`, {});
  }

  public update(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/api/questions/${id}`, data);
  }

  public createAnswer(data: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/answers/`, data);
  }

  public deleteAnswer(id: number): Observable<any> {
    return this.http.delete<any>(`${environment.apiUrl}/api/answers/${id}`, {});
  }

  public updateAnswer(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/api/answers/${id}`, data);
  }
}
