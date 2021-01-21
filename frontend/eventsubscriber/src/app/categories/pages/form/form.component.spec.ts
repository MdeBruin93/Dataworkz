import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngxs/store';

import { FormComponent } from './form.component';

describe('FormComponent', () => {
  let component: FormComponent;
  let store: Store;
  let router: Router;
  let route: ActivatedRoute;

  beforeEach(() => {
    component = new FormComponent(
      store,
      router,
      route
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
