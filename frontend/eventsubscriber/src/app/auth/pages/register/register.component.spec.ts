import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { RegisterComponent } from './register.component';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let snackBar: MatSnackBar;
  let authService: AuthService;
  let router: Router;

  beforeEach(() => {
    component = new RegisterComponent(
      snackBar,
      authService,
      router
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
