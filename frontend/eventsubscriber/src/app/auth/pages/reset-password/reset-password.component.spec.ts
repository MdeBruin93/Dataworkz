import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { ResetPasswordComponent } from './reset-password.component';

describe('ResetPasswordComponent', () => {
  let component: ResetPasswordComponent;
  let authService: AuthService;
  let route: ActivatedRoute;
  let router: Router;

  beforeEach(() => {
    component = new ResetPasswordComponent(
      authService,
      route,
      router
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
