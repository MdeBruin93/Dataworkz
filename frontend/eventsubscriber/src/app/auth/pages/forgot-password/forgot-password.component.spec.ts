import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth/services';
import createSpyObj = jasmine.createSpyObj;
import SpyObj = jasmine.SpyObj;

import { ForgotPasswordComponent } from './forgot-password.component';
import { defer } from 'rxjs';

describe('ForgotPasswordComponent', () => {
  let component: ForgotPasswordComponent;
  let routerMock: SpyObj<Router>;
  let authServiceMock: SpyObj<AuthService>;

  beforeEach(() => {
    authServiceMock = createSpyObj('QaService', ['forgotPassword']);
    routerMock = createSpyObj('router', ['navigate']);
    component = new ForgotPasswordComponent(
      authServiceMock,
      routerMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  describe('onSubmit()', () => {
    it('should do next when success', () => {
      authServiceMock.forgotPassword.and.returnValue(defer(() => Promise.resolve({})));
      component.onSubmit();

      expect(authServiceMock.forgotPassword).toHaveBeenCalled();
    });
  });
});
