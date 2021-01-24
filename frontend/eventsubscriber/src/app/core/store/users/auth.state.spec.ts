import { UsersService } from '@core/services';
import { Store } from '@ngxs/store';
import { UsersState } from './users.state';

describe('usersState', () => {
  let usersService: UsersService;
  let store: Store;
  let usersState: UsersState;

  beforeEach(() => {
    usersState = new UsersState(usersService, store);
  });

  it('should create', () => {
    expect(usersState).toBeTruthy();
  });
});