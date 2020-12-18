import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';
import { IEvent, IEventResponse, IFileResponse } from '../models';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(
    private http: HttpClient,
    private sanitizer: DomSanitizer
  ) { }

  public create(event: IEvent, formData: FormData): Observable<IEventResponse> {
    return this.http.post<IFileResponse>(`${environment.apiUrl}/api/storage/upload`, formData)
      .pipe(
        switchMap((file: IFileResponse) => {
          event.imageUrl = file.fileUrl;
          return this.http.post<IEventResponse>(`${environment.apiUrl}/api/events`, event);
        })
      );  
  }

  public storeEventImage(formData: FormData): Observable<IFileResponse> {
    return this.http.post<IFileResponse>(`${environment.apiUrl}/api/storage/upload`, formData);
  }

  public update(id:string, event: IEventResponse, formData: FormData): Observable<IEventResponse> {
    if (formData.get('file')) {
      return this.http.post<IFileResponse>(`${environment.apiUrl}/api/storage/upload`, formData)
        .pipe(
          switchMap((file: IFileResponse) => {
            event.imageUrl = file.fileUrl;
            return this.http.put<IEventResponse>(`${environment.apiUrl}/api/events/${id}`, event);
          })
        );
    }
    return this.http.put<IEventResponse>(`${environment.apiUrl}/api/events/${id}`, event);
  }

  public getAll(): Observable<IEventResponse[]> {
    return this.http.get<IEventResponse[]>(`${environment.apiUrl}/api/events/`);
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
