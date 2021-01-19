import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let http: HttpClient;

  beforeEach(() => {
    service = new UserService(
      http
    );
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
