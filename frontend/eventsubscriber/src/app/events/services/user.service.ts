import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';
import { IEvent } from '../models';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
  ) { }

  public subscribedToEvents(): Observable<IEvent[]> {
    return this.http.get<IEvent[]>(`${environment.apiUrl}/api/users/subscriptions`);
  }
}
