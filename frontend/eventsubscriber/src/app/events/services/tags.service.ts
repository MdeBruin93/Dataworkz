import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '@environments/environment';
import { Observable } from 'rxjs';
import {ITag} from '../models';

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  constructor(
    private http: HttpClient,
  ) { }

  public getAll(): Observable<ITag[]> {
    return this.http.get<ITag[]>(`${environment.apiUrl}/api/tags/`);
  }

  public create(data: ITag): Observable<ITag> {
    return this.http.post<any>(`${environment.apiUrl}/api/tags/`, data);
  }
}
