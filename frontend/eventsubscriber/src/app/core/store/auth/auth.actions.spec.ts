import { Login, SetCurrentUser } from './auth.actions';

describe('authAction', () => {
  let loginAction: Login;
  let setCurrentUser: SetCurrentUser;

  beforeEach(() => {
    loginAction = new Login('string', 'string');
    setCurrentUser = new SetCurrentUser('string');
  });

  it('should create', () => {
    expect(loginAction).toBeTruthy();
    expect(setCurrentUser).toBeTruthy();
  });
});