import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {TemplateRef, ViewContainerRef} from '@angular/core';
import {IsLoggedInDirective} from '@core/directives/is-logged-in.directive';

describe('UsersService', () => {
  let templateRefMock: SpyObj<TemplateRef<any>>;
  let viewContainerMock: ViewContainerRef;
  let component: IsLoggedInDirective;

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
    component = new IsLoggedInDirective(templateRefMock, viewContainerMock);
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });

  it('should set auth', () => {
    component.appAuth = true;
    expect(component.isLoggedIn).toBeTrue();
  });

  it('should have access when auth is true', () => {
    component.appAuth = true;

    component.ngOnInit();
    expect(viewContainerMock.createEmbeddedView).toHaveBeenCalled();
    expect(viewContainerMock.clear).not.toHaveBeenCalled();
  });

  it('should no access when auth is false', () => {
    component.ngOnInit();
    expect(viewContainerMock.createEmbeddedView).not.toHaveBeenCalled();
    expect(viewContainerMock.clear).toHaveBeenCalled();
  });
});
