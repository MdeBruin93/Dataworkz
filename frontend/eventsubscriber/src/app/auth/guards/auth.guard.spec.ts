import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { AuthGuard } from './auth.guard';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let authService: AuthService;
  let router: Router;

  beforeEach(() => {
    guard = new AuthGuard(
      authService,
      router
    );
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
