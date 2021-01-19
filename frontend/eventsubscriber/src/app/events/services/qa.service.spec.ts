import { TestBed } from '@angular/core/testing';

import { QaService } from './qa.service';

import {
  HttpTestingController,
} from '@angular/common/http/testing';
import { environment } from '@environments/environment';
import { HttpClient } from '@angular/common/http';

describe('QaService', () => {
  let service: QaService;
  let httpMock: HttpTestingController;
  let http: HttpClient;
  const data: any = [];

  beforeEach(() => {
    service = new QaService(
      http
    );
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call the getRelationId method', () => {
    service.create(data).subscribe(() => {});
    const request = httpMock.expectOne(`${environment.apiUrl}/api/questions/`);
    expect(request.request.method).toBe('POST');
  });
});
