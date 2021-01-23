import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { AuthService } from './auth.service';
import {CategoriesService} from '@categories/services';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {Category} from '@core/models';
import {defer} from 'rxjs';

describe('AuthService', () => {
  let router: SpyObj<Router>;
  let httpClientMock: SpyObj<HttpClient>;
  let authService: AuthService;

  const authObject = {
    id: 1,
    email: 'test@hr.nl',
    firstName: 'Firstname',
    lastName: 'Lastname'
  };

  beforeEach(() => {
    // @ts-ignore
    router = createSpyObj('Router', ['toStrings', 'navigate']);
    httpClientMock = createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    authService = new AuthService(httpClientMock, router);
  });

  it('should be created', () => {
    expect(authService).toBeTruthy();
  });

  describe('isAuthenticated', () => {
  });

  describe('register', () => {
    it('should register when success', () => {
      httpClientMock.post.and.returnValue(defer(() => Promise.resolve(authObject)));

      authService.register(authObject).subscribe(
        register => expect(register).toEqual(authObject, 'Expected authObject are not equal'),
        fail
      );
      expect(httpClientMock.post).toHaveBeenCalled();
    });
  });

  describe('login', () => {
    beforeEach(() => {
      spyOn(localStorage, 'setItem');
    });

    it('should login when success', () => {
      const email = 'test@test.nl';
      const pass = '12345678';

      httpClientMock.get.and.returnValue(defer(() => Promise.resolve(authObject)));

      authService.login(email, pass);
      expect(localStorage.setItem).toHaveBeenCalled();
      expect(httpClientMock.get).toHaveBeenCalled();
    });
  });

  describe('logout', () => {
    beforeEach(() => {
      spyOn(localStorage, 'clear');
    });

    it('should login when success', () => {
      authService.logout();
      expect(localStorage.clear).toHaveBeenCalled();
      expect(router.navigate).toHaveBeenCalled();
    });
  });

  describe('forgotPassword', () => {
    it('should send a forgot password request when success', () => {
      httpClientMock.post.and.returnValue(defer(() => Promise.resolve(authObject)));

      authService.forgotPassword(authObject).subscribe(
        forgot => expect(forgot).toEqual(authObject, 'Expected authObject are not equal'),
        fail
      );
      expect(httpClientMock.post).toHaveBeenCalled();
    });
  });

  describe('resetPassword', () => {
    it('should reset password when success', () => {
      httpClientMock.post.and.returnValue(defer(() => Promise.resolve(authObject)));

      authService.resetPassword(authObject).subscribe(
        reset => expect(reset).toEqual(authObject, 'Expected authObject are not equal'),
        fail
      );
      expect(httpClientMock.post).toHaveBeenCalled();
    });
  });

  describe('activateAccount', () => {
    it('should account when success', () => {
      httpClientMock.post.and.returnValue(defer(() => Promise.resolve(authObject)));

      authService.resetPassword(authObject).subscribe(
        reset => expect(reset).toEqual(authObject, 'Expected authObject are not equal'),
        fail
      );
      expect(httpClientMock.post).toHaveBeenCalled();
    });
  });
});
