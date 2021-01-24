import { Injectable } from '@angular/core';
import { State, Selector, StateContext, Action } from '@ngxs/store';

import { AuthStateModel } from './auth.state-model';
import { Login, Logout, SetCurrentUser } from './auth.actions';
import { IUser } from '@core/models/user.model';

import { AuthService } from '@auth/services';
@State<AuthStateModel>({
  name: 'auth',
  defaults: {
    currentUser: null,
    token: ""
  },
})
@Injectable()
export class AuthState {
  public constructor(
    private authService: AuthService
  ) {}

  @Selector()
  static currentUser(state: AuthStateModel): IUser | null {
    return state.currentUser;
  }

  @Selector()
  static token(state: AuthStateModel): string | null {
    return state.token;
  }

  @Selector()
  static isLoggedIn(state: AuthStateModel): boolean | null {
    return state.token != '';
  }

  @Action(Login)
  public async login(ctx: StateContext<AuthStateModel>, { email, password }: Login): Promise<void> {
    const currentUser = await this.authService.login(email, password).toPromise();
    const token = this.authService.getToken();

    if (currentUser && token) {
      ctx.patchState({ currentUser: currentUser, token: token});
    }
  }

  @Action(Logout)
  public logout(ctx: StateContext<AuthStateModel>): void {
    this.authService.logout();
    ctx.patchState({ currentUser: null, token: '' });
  }

  @Action(SetCurrentUser)
  public async SetCurrentUser(ctx: StateContext<AuthStateModel>, { data }: SetCurrentUser): Promise<void> {
    if (data) {
      ctx.patchState({ currentUser: data});
    }
  }
}
