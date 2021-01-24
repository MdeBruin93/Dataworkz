import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { ForgotPasswordComponent } from './forgot-password.component';

describe('ForgotPasswordComponent', () => {
  let component: ForgotPasswordComponent;
  let authService: AuthService;
  let router: Router;

  beforeEach(() => {
    component = new ForgotPasswordComponent(
      authService,
      router
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
