import { Injectable } from '@angular/core';
import { State, Selector, StateContext, Action, Store } from '@ngxs/store';

import { UsersStateModel } from './users.state-model';
import { LoadUsers, LoadBlockedUsers, BlockUser, DeBlockUser, EditAccount } from './users.actions';
import { SetCurrentUser } from '../auth/auth.actions';
import { IUser } from '@core/models/user.model';

import { UsersService } from '@core/services';



@State<UsersStateModel>({
  name: 'users',
  defaults: {
    users: [],
    blockedUsers: []
  },
})
@Injectable()
export class UsersState {
  public constructor(
    private usersService: UsersService,
    private store: Store
  ) {}

  @Selector()
  static users(state: UsersStateModel): IUser[] {
    return state.users;
  }

  @Selector()
  static blockedUsers(state: UsersStateModel): IUser[] {
    return state.blockedUsers;
  }

  @Action(LoadUsers)
  public async LoadUsers(ctx: StateContext<UsersStateModel>, {}: LoadUsers): Promise<void> {
    const users = await this.usersService.getUsers().toPromise();
    ctx.patchState({ users: users });
  }

  @Action(LoadBlockedUsers)
  public async LoadBlockedUsers(ctx: StateContext<UsersStateModel>, {}: LoadUsers): Promise<void>{
    const blockedUsers = await this.usersService.getBlockedUsers().toPromise();
    ctx.patchState({ blockedUsers: blockedUsers });
  }

  @Action(BlockUser)
  public async BlockUser(ctx: StateContext<UsersStateModel>, { id }: BlockUser): Promise<void>{
    await this.usersService.update(id, true).toPromise();
    const users = await this.usersService.getUsers().toPromise();
    const blockedUsers = await this.usersService.getBlockedUsers().toPromise();
    ctx.patchState({ users: users, blockedUsers: blockedUsers });
  }

  @Action(DeBlockUser)
  public async DeBlockUser(ctx: StateContext<UsersStateModel>, { id }: DeBlockUser): Promise<void>{
    await this.usersService.update(id, false).toPromise();
    const users = await this.usersService.getUsers().toPromise();
    const blockedUsers = await this.usersService.getBlockedUsers().toPromise();
    ctx.patchState({ users: users, blockedUsers: blockedUsers });
  }

  @Action(EditAccount)
  public async EditAction(ctx: StateContext<UsersStateModel>, { data }: EditAccount): Promise<void>{
    const user = await this.usersService.editAccount(data).toPromise();
    if (user) {
        this.store.dispatch(new SetCurrentUser(user));
    }
  }
}
