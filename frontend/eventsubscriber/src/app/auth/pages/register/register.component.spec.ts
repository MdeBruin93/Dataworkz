import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { RegisterComponent } from './register.component';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {defer} from 'rxjs';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let snackBarMock: SpyObj<MatSnackBar>;
  let routerMock: SpyObj<Router>;
  let authServiceMock: SpyObj<AuthService>;

  beforeEach(() => {
    snackBarMock = createSpyObj(['open']);
    routerMock = createSpyObj(['navigate']);
    authServiceMock = createSpyObj('authService', ['register']);
    component = new RegisterComponent(
      snackBarMock,
      authServiceMock,
      routerMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('onSubmit', () => {
    it('should register', () => {
      authServiceMock.register.and.returnValue(defer(() => Promise.resolve()));

      component.onSubmit();
      expect(authServiceMock.register).toHaveBeenCalled();
    });

    it('should not register', () => {
      authServiceMock.register.and.returnValue(defer(() => Promise.reject()));

      component.onSubmit();
    });
  });
});
