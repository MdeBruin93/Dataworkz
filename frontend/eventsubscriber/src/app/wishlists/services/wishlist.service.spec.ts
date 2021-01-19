import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { WishlistService } from './wishlist.service';

describe('WishlistService', () => {
  let service: WishlistService;
  let http: HttpClient;

  beforeEach(() => {
    service = new WishlistService(
      http
    );
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
