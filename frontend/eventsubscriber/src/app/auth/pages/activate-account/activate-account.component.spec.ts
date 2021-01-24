import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth/services';

import { ActivateAccountComponent } from './activate-account.component';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {defer} from "rxjs";

describe('ActivateAccountComponent', () => {
  let component: ActivateAccountComponent;
  let routerMock: SpyObj<Router>;
  let activatedRouteMock: SpyObj<ActivatedRoute>;
  let authServiceMock: SpyObj<AuthService>;

  beforeEach(() => {
    routerMock = createSpyObj('Router', ['']);
    activatedRouteMock = createSpyObj('ActivatedRoute', [''], {snapshot: {params: {token: undefined}}});
    authServiceMock = createSpyObj('AuthService', ['activateAccount']);
    component = new ActivateAccountComponent(
      routerMock,
      activatedRouteMock,
      authServiceMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should activate account', () => {
    authServiceMock.activateAccount.and.returnValue(defer(() => Promise.resolve()));

    component.ngOnInit();
    expect(authServiceMock.activateAccount).toHaveBeenCalled();
  });
});
