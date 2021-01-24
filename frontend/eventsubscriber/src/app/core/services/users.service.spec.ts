import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { UsersService } from './users.service';

describe('UsersService', () => {
  let service: UsersService;
  let http: HttpClient;

  beforeEach(() => {
    service = new UsersService(
      http
    );
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
