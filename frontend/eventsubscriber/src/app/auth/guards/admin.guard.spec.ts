import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { AdminGuard } from './admin.guard';

describe('AdminGuard', () => {
  let guard: AdminGuard;
  let authService: AuthService;
  let router: Router;

  beforeEach(() => {
    guard = new AdminGuard(
      authService,
      router
    );
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
