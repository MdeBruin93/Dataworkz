import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';
import { IEvent, IEventResponse } from '../models';

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(
    private http: HttpClient
  ) { }

  public create(event: IEvent): Observable<IEventResponse> {
    return this.http.post<IEventResponse>(`${environment.apiUrl}/api/events`, event);
  }

  public getAll(): Observable<IEventResponse[]> {
    return this.http.get<IEventResponse[]>(`${environment.apiUrl}/api/events`);
  }

  public findById(id: number): Observable<IEventResponse> {
    return this.http.get<IEventResponse>(`${environment.apiUrl}/api/events/${id}`);
  }
}
