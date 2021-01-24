import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngxs/store';

import { FormComponent } from './form.component';
import {DetailComponent} from "../../../events/pages";
import createSpyObj = jasmine.createSpyObj;
import SpyObj = jasmine.SpyObj;
import Spy = jasmine.Spy;
import {CategoriesState} from "@core/store";

describe('FormComponent', () => {
  let storeMock: SpyObj<Store>;
  let routerMock: SpyObj<Router>;
  let activatedRouterMock: SpyObj<ActivatedRoute>;
  let component: FormComponent;

  beforeEach(() => {
    storeMock = createSpyObj('Store', ['dispatch']);
    routerMock = createSpyObj('Router', ['navigate']);
    activatedRouterMock = createSpyObj('ActivatedRoute', ['toStrings'], {snapshot: {params: {id: undefined}}});
    component = new FormComponent(storeMock, routerMock, activatedRouterMock);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should do nothing when id is not set', () => {
      component.ngOnInit();
      expect(storeMock.dispatch).not.toHaveBeenCalled();
    });
  });

  describe('saveCategory', () => {
    it('should save a category when function is called', () => {
      component.saveCategory();
      expect(storeMock.dispatch).toHaveBeenCalledTimes(2);
      expect(routerMock.navigate).toHaveBeenCalledWith(['/categories']);
    });
  });
});
