import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { EventsService } from '../../services';

import { FormComponent } from './form.component';

describe('FormComponent', () => {
  let component: FormComponent;
  let eventService: EventsService;
  let route: ActivatedRoute;
  let router: Router;
  let snackBar: MatSnackBar;
  let store: Store;

  beforeEach(() => {
    component = new FormComponent(
      eventService,
      route,
      router,
      snackBar,
      store
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
