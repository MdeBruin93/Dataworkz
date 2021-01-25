import { AuthService } from '@auth/services';
import { StateContext } from '@ngxs/store';
import { Login, SetCurrentUser } from './auth.actions';
import { AuthState } from './auth.state';
import { AuthStateModel } from './auth.state-model';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;

describe('authAction', () => {
  let authServiceMock: SpyObj<AuthService>;
  let authState: AuthState;

  beforeEach(() => {
    authServiceMock = createSpyObj('AuthService', ['login', 'getToken']);
    authState = new AuthState(authServiceMock);
  });

  it('should create', () => {
    expect(authState).toBeTruthy();
  });

  // describe('currentUser()', () => {
  //   it('should return curent user', () => {
  //     const ctx: StateContext<AuthStateModel> = 
  //     authState.login([], { 'email', 'password'});
  //     expect(eventServiceMock.findById).not.toHaveBeenCalled();
  //   });
  // });

});