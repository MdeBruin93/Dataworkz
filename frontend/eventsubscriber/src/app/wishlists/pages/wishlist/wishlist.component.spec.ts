import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WishlistService } from '../../services';

import { WishlistComponent } from './wishlist.component';

describe('WishlistComponent', () => {
  let component: WishlistComponent;
  let wishlistService: WishlistService;
  let snackBar: MatSnackBar;

  beforeEach(() => {
    component = new WishlistComponent(
      wishlistService,
      snackBar
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
