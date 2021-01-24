import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { WishlistService } from './wishlist.service';
import {UsersService} from '@core/services';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {Wishlist} from '../models';
import {environment} from '@environments/environment';
import {defer} from 'rxjs';

describe('WishlistService', () => {
  let httpClientMock: SpyObj<HttpClient>;
  let wishlistService: WishlistService;

  const wishlistObj = {
    name: 'Wishlist'
  };

  beforeEach(() => {
    httpClientMock = createSpyObj('HttpClient', ['get', 'post', 'put', 'delete']);
    wishlistService = new WishlistService(httpClientMock);
  });

  it('should be created', () => {
    expect(wishlistService).toBeTruthy();
  });

  describe('findByUser', () => {
    it('should find wishlists of the logged in user', () => {
      const expectedWishlist: any = wishlistObj;

      httpClientMock.get.withArgs(`${environment.apiUrl}/api/wishlists/findbyuser`)
        .and.returnValue(defer(() => Promise.resolve(expectedWishlist)));

      wishlistService.findByUser().subscribe(
        wishlist => expect(wishlist).toEqual(expectedWishlist),
        fail
      );
      expect(httpClientMock.get).toHaveBeenCalled();
    });
  });

  describe('update', () => {
    it('should update wishlists when success', () => {
      const expectedWishlist: any = wishlistObj;
      const id = 1;

      httpClientMock.put.withArgs(`${environment.apiUrl}/api/wishlists/${id}`, expectedWishlist)
        .and.returnValue(defer(() => Promise.resolve(expectedWishlist)));

      wishlistService.update(id, expectedWishlist).subscribe(
        wishlist => expect(wishlist).toEqual(expectedWishlist),
        fail
      );
      expect(httpClientMock.put).toHaveBeenCalled();
    });
  });

  describe('create', () => {
    it('should create wishlists when success', () => {
      const expectedWishlist: any = wishlistObj;
      const id = 1;

      httpClientMock.post.withArgs(`${environment.apiUrl}/api/wishlists/`, expectedWishlist)
        .and.returnValue(defer(() => Promise.resolve(expectedWishlist)));

      wishlistService.create(expectedWishlist).subscribe(
        wishlist => expect(wishlist).toEqual(expectedWishlist),
        fail
      );
      expect(httpClientMock.post).toHaveBeenCalled();
    });
  });

  describe('delete', () => {
    it('should delete a wishlist when success', () => {
      const id = 1;

      wishlistService.delete(id);
      expect(httpClientMock.delete).toHaveBeenCalled();
    });
  });
});
