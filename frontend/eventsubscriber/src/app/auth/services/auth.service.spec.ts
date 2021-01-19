import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let router: Router;
  let httpClient: HttpClient;

  beforeEach(() => {
    service = new AuthService(
      httpClient,
      router
    );
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
