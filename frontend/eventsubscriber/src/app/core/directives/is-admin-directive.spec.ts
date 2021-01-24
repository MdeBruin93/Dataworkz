import { HttpClient } from '@angular/common/http';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {IUser} from '@core/models';
import {defer} from 'rxjs';
import {environment} from '@environments/environment';
import {TemplateRef, ViewContainerRef} from '@angular/core';
import {IsAdminDirective} from '@core/directives/is-admin.directive';

describe('UsersService', () => {
  let templateRefMock: SpyObj<TemplateRef<any>>;
  let viewContainerMock: ViewContainerRef;
  let component: IsAdminDirective;

  const userObject = {
    id: 1,
    email: 'test@hr.nl',
    firstName: 'Firstname',
    lastName: 'Lastname',
    role: 'ROLE_ADMIN'
  };

  beforeEach(() => {
    templateRefMock = createSpyObj('TemplateRef', ['get', 'post', 'put', 'delete']);
    viewContainerMock = createSpyObj('ViewContainerRef', ['createEmbeddedView', 'clear']);
    component = new IsAdminDirective(templateRefMock, viewContainerMock);
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should set is admin when role is admin', () => {
    component.appIsAdmin = 'ROLE_ADMIN';
    expect(component.isAdmin).toBeTrue();
  });

  it('should have access when user is admin', () => {
    component.appIsAdmin = 'ROLE_ADMIN';

    component.ngOnInit();
    expect(viewContainerMock.createEmbeddedView).toHaveBeenCalled();
    expect(viewContainerMock.clear).not.toHaveBeenCalled();
  });

  it('should no access when user is not admin', () => {
    component.ngOnInit();
    expect(viewContainerMock.createEmbeddedView).not.toHaveBeenCalled();
    expect(viewContainerMock.clear).toHaveBeenCalled();
  });
});
