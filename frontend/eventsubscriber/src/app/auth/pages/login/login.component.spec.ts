import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';

import { LoginComponent } from './login.component';
import SpyObj = jasmine.SpyObj;
import {defer} from 'rxjs';
import createSpyObj = jasmine.createSpyObj;

describe('LoginComponent', () => {
  let component: LoginComponent;
  let storeMock: SpyObj<Store>;
  let routerMock: SpyObj<Router>;
  let snackBarMock: SpyObj<MatSnackBar>;

  beforeEach(() => {
    storeMock = createSpyObj(['dispatch']);
    routerMock = createSpyObj(['open']);
    snackBarMock = createSpyObj(['open']);
    component = new LoginComponent(
      storeMock,
      routerMock,
      snackBarMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
