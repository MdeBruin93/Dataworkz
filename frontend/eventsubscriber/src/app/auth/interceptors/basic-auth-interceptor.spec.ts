import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {BasicAuthInterceptor} from '@auth/interceptors/basic-auth.interceptor';
import {AuthService} from '@auth/services';
import {HttpHandler, HttpRequest} from '@angular/common/http';

describe('BasicAuthInterceptor', () => {
  let component: BasicAuthInterceptor;
  let httpRequestMock: SpyObj<HttpRequest<any>>;
  let httpHandlerMock: SpyObj<HttpHandler>;
  let authServiceMock: SpyObj<AuthService>;

  beforeEach(() => {
    httpRequestMock = createSpyObj(['clone']);
    httpHandlerMock = createSpyObj(['handle']);
    authServiceMock = createSpyObj(['getToken']);
    component = new BasicAuthInterceptor(
      authServiceMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should do nothing when no token is returned', () => {
    authServiceMock.getToken.and.returnValue('');

    component.intercept(httpRequestMock, httpHandlerMock);
    expect(httpRequestMock.clone).not.toHaveBeenCalled();
    expect(httpHandlerMock.handle).toHaveBeenCalled();
  });

  it('should do something when token is returned', () => {
    authServiceMock.getToken.and.returnValue('abc');

    component.intercept(httpRequestMock, httpHandlerMock);
    expect(httpRequestMock.clone).toHaveBeenCalled();
    expect(httpHandlerMock.handle).toHaveBeenCalled();
  });
});
