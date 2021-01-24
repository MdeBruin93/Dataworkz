import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {IUser} from '@core/models';
import {TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthService} from '@auth/services';
import {IsNotAllowedForOrganizerDirective} from '@shared/directives/isNotAllowedForOrganizer.directive';

describe('IsNotAllowedForOrganizerDirective', () => {
  let templateRefMock: SpyObj<TemplateRef<any>>;
  let authServiceMock: SpyObj<AuthService>;
  let viewContainerMock: ViewContainerRef;
  let component: IsNotAllowedForOrganizerDirective;

  const userObject = {
    id: 1,
    email: 'test@hr.nl',
    firstName: 'Firstname',
    lastName: 'Lastname',
    role: 'ROLE_ADMIN'
  };

  beforeEach(() => {
    templateRefMock = createSpyObj('TemplateRef', ['get', 'post', 'put', 'delete']);
    authServiceMock = createSpyObj('AuthService', ['isAuthenticated', 'getCurrentUser']);
    viewContainerMock = createSpyObj('ViewContainerRef', ['createEmbeddedView', 'clear']);
    component = new IsNotAllowedForOrganizerDirective(templateRefMock, authServiceMock, viewContainerMock);
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should set a owner id', () => {
    const ownerId = 1;
    component.appIsNotAllowedForOrganizer = 1;
    expect(component.ownerId).toEqual(ownerId);
  });

  it('should have access', () => {
    const ownerId = 1;
    component.appIsNotAllowedForOrganizer = ownerId;
    const currentUser: IUser = {
      id: 2,
      email: 'test@hr.nl',
      firstName: 'Firstname',
      lastName: 'Lastname',
      role: 'ROLE_ADMIN'
    };

    authServiceMock.isAuthenticated.and.returnValue(true);
    authServiceMock.getCurrentUser.and.returnValue(currentUser);

    component.ngOnInit();
    expect(viewContainerMock.createEmbeddedView).toHaveBeenCalled();
    expect(viewContainerMock.clear).not.toHaveBeenCalled();
  });

  it('should no access when user is not admin', () => {
    const ownerId = 1;
    component.appIsNotAllowedForOrganizer = ownerId;
    const currentUser: IUser = {
      id: 1,
      email: 'test@hr.nl',
      firstName: 'Firstname',
      lastName: 'Lastname',
      role: 'ROLE_ADMIN'
    };

    authServiceMock.isAuthenticated.and.returnValue(true);
    authServiceMock.getCurrentUser.and.returnValue(currentUser);

    component.ngOnInit();
    expect(viewContainerMock.createEmbeddedView).not.toHaveBeenCalled();
    expect(viewContainerMock.clear).toHaveBeenCalled();
  });
});
