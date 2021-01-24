import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { UsersService } from './users.service';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {IUser} from '@core/models';
import {defer} from 'rxjs';
import {environment} from '@environments/environment';

describe('UsersService', () => {
  let httpClientMock: SpyObj<HttpClient>;
  let usersService: UsersService;

  const userObject = {
    id: 1,
    email: 'test@hr.nl',
    firstName: 'Firstname',
    lastName: 'Lastname',
    role: 'ROLE_ADMIN'
  };

  beforeEach(() => {
    httpClientMock = createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    usersService = new UsersService(httpClientMock);
  });

  it('should be created', () => {
    expect(usersService).toBeTruthy();
  });

  describe('getUsers', () => {
    it('should get users when success', () => {
      const expectedUsers: IUser[] = [userObject];

      httpClientMock.get.withArgs(`${environment.apiUrl}/api/users`)
        .and.returnValue(defer(() => Promise.resolve(expectedUsers)));

      usersService.getUsers().subscribe(
        users => expect(users).toEqual(expectedUsers),
        fail
      );
      expect(httpClientMock.get).toHaveBeenCalled();
    });
  });

  describe('getBlockedUser', () => {
    it('should get blocked users when success', () => {
      const expectedUsers: IUser[] = [userObject];

      httpClientMock.get.withArgs(`${environment.apiUrl}/api/users/blocked`)
        .and.returnValue(defer(() => Promise.resolve(expectedUsers)));

      usersService.getBlockedUsers().subscribe(
        users => expect(users).toEqual(expectedUsers),
        fail
      );
      expect(httpClientMock.get).toHaveBeenCalled();
    });
  });

  describe('update', () => {
    it('should update when success', () => {
      const expectedUser: IUser = userObject;
      const id = 1;
      const block = true;

      httpClientMock.put.withArgs(`${environment.apiUrl}/api/users/` + id, {blocked: block})
        .and.returnValue(defer(() => Promise.resolve(expectedUser)));

      usersService.update(id, true).subscribe(
        users => expect(users).toEqual(expectedUser),
        fail
      );
      expect(httpClientMock.put).toHaveBeenCalled();
    });
  });

  describe('editAccount', () => {
    it('should update when success', () => {
      const expectedUser: IUser = userObject;
      const id = 1;

      httpClientMock.put.withArgs(`${environment.apiUrl}/api/auth/my`, expectedUser)
        .and.returnValue(defer(() => Promise.resolve(expectedUser)));

      usersService.editAccount(expectedUser).subscribe(
        users => expect(users).toEqual(expectedUser),
        fail
      );
      expect(httpClientMock.put).toHaveBeenCalled();
    });
  });
});
