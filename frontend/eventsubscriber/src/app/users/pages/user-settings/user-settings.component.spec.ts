import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';

import { UserSettingsComponent } from './user-settings.component';

describe('UserSettingsComponent', () => {
  let component: UserSettingsComponent;
  let store: Store;
  let route: Router;

  beforeEach(() => {
    component = new UserSettingsComponent(
      store,
      route
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
