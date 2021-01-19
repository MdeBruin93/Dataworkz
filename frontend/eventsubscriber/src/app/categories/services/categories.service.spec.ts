import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { CategoriesService } from './categories.service';

describe('CategoriesService', () => {
  let service: CategoriesService;
  let httpClient: HttpClient;

  beforeEach(() => {
    service = new CategoriesService(
      httpClient
    );
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
