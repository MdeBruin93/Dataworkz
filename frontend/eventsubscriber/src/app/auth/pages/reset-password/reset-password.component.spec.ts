import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth/services';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;

import { ResetPasswordComponent } from './reset-password.component';
import { defer } from 'rxjs';

describe('ResetPasswordComponent', () => {
  let component: ResetPasswordComponent;
  let authService: AuthService;
  let authServiceMock: SpyObj<AuthService>;
  let activatedRouterMock: SpyObj<ActivatedRoute>;
  let routerMock: SpyObj<Router>;

  beforeEach(() => {
    authServiceMock = createSpyObj('AuthService', ['resetPassword']);
    activatedRouterMock = createSpyObj('ActivatedRoute', ['toStrings', 'navigate'], {snapshot: {params: {token: undefined}}});
    routerMock = createSpyObj('Router', ['navigate']);
    component = new ResetPasswordComponent(
      authServiceMock,
      activatedRouterMock,
      routerMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should set token from url', () => {
      // @ts-ignore
      Object.getOwnPropertyDescriptor(activatedRouterMock, 'snapshot').get.and.returnValue({params: {token: '1'}});
      component.ngOnInit();
      expect(component.token).toEqual('1');
    });
  });

  describe('isPasswordsEqual()', () => {
    it('should return that passwords are equal', () => {
      component.resetPasswordForm.value.newPassword = '123';
      component.resetPasswordForm.value.repeatNewPassword = '123';

      component.isPasswordsEqual();
      expect(component.isPasswordsEqual()).toBeTruthy();
    });

    it('should not return that passwords are equal', () => {
      component.resetPasswordForm.value.newPassword = '12';
      component.resetPasswordForm.value.repeatNewPassword = '123';

      component.isPasswordsEqual();
      expect(component.isPasswordsEqual()).toBeFalsy();
    });
  });


  describe('onSubmit()', () => {
    it('should reset password and navigate user', () => {
      authServiceMock.resetPassword.and.returnValue(defer(() => Promise.resolve()));

      component.onSubmit();
      expect(authServiceMock.resetPassword).toHaveBeenCalled();
    });
  });
});
