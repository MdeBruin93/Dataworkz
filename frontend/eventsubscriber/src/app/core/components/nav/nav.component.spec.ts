import { BreakpointObserver, LayoutModule } from '@angular/cdk/layout';
import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';

import { NavComponent } from './nav.component';
import { Store } from '@ngxs/store';

describe('NavComponent', () => {
  let component: NavComponent;
  let breakpointObserver: BreakpointObserver;
  let store: Store;

  beforeEach(() => {
    component = new NavComponent(
      breakpointObserver,
      store
    );
  });

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
