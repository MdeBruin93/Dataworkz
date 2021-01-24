import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { AdminGuard } from './admin.guard';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;

describe('AdminGuard', () => {
  let guard: AdminGuard;
  let authServiceMock: SpyObj<AuthService>;
  let routerMock: SpyObj<Router>;

  beforeEach(() => {
    authServiceMock = createSpyObj('AuthService', ['isAuthenticated', 'isAdmin']);
    routerMock = createSpyObj('Router', ['createEmbeddedView', 'clear']);
    guard = new AdminGuard(
      authServiceMock,
      routerMock
    );
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
