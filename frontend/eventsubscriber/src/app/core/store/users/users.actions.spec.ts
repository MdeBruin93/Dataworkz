import { BlockUser, DeBlockUser, EditAccount } from './users.actions';

describe('usersAction', () => {
  let blockUser: BlockUser;
  let deBlockUser: DeBlockUser;
  let editAccount: EditAccount;

  beforeEach(() => {
    blockUser = new BlockUser(1);
    deBlockUser = new DeBlockUser(1);
    editAccount = new EditAccount(1);
  });

  it('should create', () => {
    expect(blockUser).toBeTruthy();
    expect(deBlockUser).toBeTruthy();
    expect(editAccount).toBeTruthy();
  });
});