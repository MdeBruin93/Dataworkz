import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';

import { OverviewComponent } from './overview.component';

describe('OverviewComponent', () => {
  let component: OverviewComponent;
  let store: Store;
  let router: Router;

  beforeEach(() => {
    component = new OverviewComponent(
      store,
      router
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
