import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Store } from '@ngxs/store';

import { OverviewComponent } from './overview.component';

describe('OverviewComponent', () => {
  let component: OverviewComponent;
  let store: Store;

  beforeEach(() => {
    component = new OverviewComponent(
      store
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
