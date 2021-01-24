import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let store: Store;
  let router: Router;
  let snackBar: MatSnackBar;

  beforeEach(() => {
    component = new LoginComponent(
      store,
      router,
      snackBar
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
