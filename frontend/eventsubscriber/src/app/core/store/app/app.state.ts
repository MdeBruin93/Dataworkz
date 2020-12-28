import { Injectable } from '@angular/core';
import { State, Selector, StateContext, Action } from '@ngxs/store';

import { AppStateModel } from './app.state-model';
import { SetTitle, ToggleSidebar } from './app.actions';

@State<AppStateModel>({
  name: 'app',
  defaults: {
    initialized: false,
    sidebarVisible: true,
    title: 'Dashboard'
  },
})
@Injectable()
export class AppState {
  @Selector()
  static initialized(state: AppStateModel): boolean {
    return state.initialized;
  }

  @Selector()
  static title(state: AppStateModel): string {
    return state.title;
  }

  @Selector()
  static sidebarVisible(state: AppStateModel): boolean {
    return state.sidebarVisible;
  }

  @Action(SetTitle)
  public setTitle(ctx: StateContext<AppStateModel>, { title }: SetTitle): void {
    ctx.patchState({ title });
  }

  @Action(ToggleSidebar)
  public toggleSidebar(ctx: StateContext<AppStateModel>): void {
    const state = ctx.getState();
    ctx.patchState({ sidebarVisible: !state.sidebarVisible });
  }
}
