import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { ActivateAccountComponent } from './activate-account.component';

describe('ActivateAccountComponent', () => {
  let component: ActivateAccountComponent;
  let router: Router;
  let route: ActivatedRoute;
  let authService: AuthService;

  beforeEach(() => {
    component = new ActivateAccountComponent(
      router,
      route,
      authService
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
