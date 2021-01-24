import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { WishlistService } from 'src/app/wishlists';
import { EventsService, UserService } from '../../services';

import { OverviewComponent } from './overview.component';

describe('OverviewComponent', () => {
  let component: OverviewComponent;
  let eventsService: EventsService;
  let wishlistService: WishlistService;
  let userService: UserService;
  let router: Router;
  let store: Store;
  let dialog: MatDialog;

  beforeEach(() => {
    component = new OverviewComponent(
      eventsService,
      wishlistService,
      userService,
      router,
      store,
      dialog
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
