import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;

import { OverviewComponent } from './overview.component';
import { DeleteCategory, LoadCategories } from '@core/store';

describe('OverviewComponent', () => {
  let component: OverviewComponent;
  let store: Store;
  let storeMock: SpyObj<Store>;
  let router: Router;
  let routerMock: SpyObj<Router>;

  beforeEach(() => {
    storeMock = createSpyObj('Store', ['dispatch']);
    routerMock = createSpyObj('Router', ['navigate']);
    component = new OverviewComponent(
      storeMock,
      routerMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit()', () => {
    it('should dispatch LoadCategories', () => {
      component.ngOnInit();
      expect(storeMock.dispatch).toHaveBeenCalledWith(new LoadCategories());
    });
  });

  describe('deletecategory()', () => {
    it('should navigate to create page', () => {
      component.deletecategory(1);
      expect(storeMock.dispatch).toHaveBeenCalledWith(new DeleteCategory(1));
    });
  });

  describe('edit()', () => {
    it('should navigate to create page', () => {
      component.edit();
      expect(routerMock.navigate).toHaveBeenCalledWith(["categories/create"]);
    });
  });
});
