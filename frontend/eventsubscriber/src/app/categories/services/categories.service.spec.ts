import { HttpClient } from '@angular/common/http';
import { CategoriesService } from './categories.service';
import {ICategory} from '@core/interfaces';
import createSpyObj = jasmine.createSpyObj;
import SpyObj = jasmine.SpyObj;
import {defer} from 'rxjs';
import {Category} from '@core/models';
import {environment} from '@environments/environment';

describe('CategoriesService', () => {
  let categoriesService: CategoriesService;
  let httpClientMock: SpyObj<HttpClient>;


  const categoryObject = {
    id: 1,
    name: 'Category',
    color: 'Red',
    endDate: undefined,
    deleted: true
  };

  beforeEach(() => {
    httpClientMock = createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    categoriesService = new CategoriesService(httpClientMock);
  });

  it('should be created', () => {
    expect(categoriesService).toBeTruthy();
  });

  describe('getAll', () => {
    it('should return a list of categories when success', () => {
      const expectedCategories: Category[] = [categoryObject];

      httpClientMock.get.and.returnValue(defer(() => Promise.resolve(expectedCategories)));

      categoriesService.getAll().subscribe(
        categories => expect(categories).toEqual(expectedCategories, 'Expected categories are not equal'),
        fail
      );
      expect(httpClientMock.get).toHaveBeenCalled();
    });
  });

  describe('findById', () => {
    it('should return a object when succes', () => {
      const expectedCategory: Category = categoryObject;

      httpClientMock.get.and.returnValue(defer(() => Promise.resolve(expectedCategory)));

      categoriesService.findById(1).subscribe(
        category => expect(category).toEqual(expectedCategory, 'Expected categories are not equal'),
        fail
      );
      expect(httpClientMock.get).toHaveBeenCalled();
    });
  });

  describe('save', () => {
    it('should save a object when category id is not set', () => {
      const parentCategory: Category = categoryObject;
      const expectedCategory = {...parentCategory, ...{id: undefined}};

      httpClientMock.post.and.returnValue(defer(() => Promise.resolve(expectedCategory)));

      categoriesService.save(expectedCategory).subscribe(
        category => expect(category).toEqual(category),
        fail
      );
      expect(httpClientMock.post).toHaveBeenCalled();
      expect(httpClientMock.put).not.toHaveBeenCalled();
    });

    it('should update a object when category id is set', () => {
      const expectedCategory: Category = categoryObject;

      httpClientMock.put.and.returnValue(defer(() => Promise.resolve(expectedCategory)));

      categoriesService.save(expectedCategory).subscribe(
        category => expect(category).toEqual(category),
        fail
      );
      expect(httpClientMock.post).not.toHaveBeenCalled();
      expect(httpClientMock.put).toHaveBeenCalled();
    });
  });

  describe('delete', () => {
    it('should delete a object', () => {
      categoriesService.delete(1);

      expect(httpClientMock.delete).toHaveBeenCalled();
    });
  });
});
