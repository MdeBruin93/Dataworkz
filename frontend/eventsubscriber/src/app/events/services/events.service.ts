import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';
import { IEvent, IEventResponse } from '../models';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(
    private http: HttpClient,
    private sanitizer: DomSanitizer
  ) { }

  public create(formData: FormData): Observable<IEventResponse> {
    return this.http.post<IEventResponse>(`${environment.apiUrl}/api/events/`, formData);
  }

  public update(id:string, event: IEvent): Observable<IEventResponse> {
    return this.http.put<IEventResponse>(`${environment.apiUrl}/api/events/${id}`, event);
  }

  public getOneWithId(id: string): Observable<IEventResponse> {
    return this.http.get<IEventResponse>(`${environment.apiUrl}/api/events/${id}`);
  }

  public getAll(): Observable<IEvent[]> {
    return this.http.get<IEvent[]>(`${environment.apiUrl}/api/events/`);
  }

  public findById(id: number): Observable<IEventResponse> {
    return this.http.get<IEventResponse>(`${environment.apiUrl}/api/events/${id}`);
  }

  public subscribe(id: number): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/api/events/${id}/subscribe`, {});
  }

  public sanitize(url:string): SafeHtml{
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }
}
