import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';

import { Store, Select } from '@ngxs/store';
import { AuthState, Logout } from '@core/store';
import { IUser } from '@core/models';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent {
  @Select(AuthState.isLoggedIn)
  public isLoggedIn$: Observable<boolean>;

  @Select(AuthState.currentUser)
  public currentUser$: Observable<IUser>;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    private breakpointObserver: BreakpointObserver,
    private store: Store
  ) {}

  public logout() {
    this.store.dispatch(new Logout());
  }

}
