import { Component, OnInit } from '@angular/core';
import { UsersState, LoadUsers, LoadBlockedUsers, BlockUser, DeBlockUser, AuthState } from '@core/store';
import { IUser } from '@core/models';
import { Store, Select } from '@ngxs/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  @Select(UsersState.users)
  public users$: Observable<IUser[]>;

  @Select(UsersState.blockedUsers)
  public blockedUsers$: Observable<IUser[]>;

  @Select(AuthState.currentUser)
  public currentUser$: Observable<IUser>;

  displayedColumns: string[] = ['firstName', 'lastName', 'blocked', 'actions'];

  constructor(
    private store: Store
  ) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadUsers());
    this.store.dispatch(new LoadBlockedUsers());
  }

  blockUser(id: number) {
    this.store.dispatch(new BlockUser(id));
  }

  deBlockUser(id: number) {
    this.store.dispatch(new DeBlockUser(id));
  }

}
