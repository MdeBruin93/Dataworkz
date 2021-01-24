import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WishlistService } from '../../services';

import { WishlistComponent } from './wishlist.component';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {IUser} from '@core/models';
import {defer} from "rxjs";

describe('WishlistComponent', () => {
  let component: WishlistComponent;
  let wishlistServiceMock: SpyObj<WishlistService>;
  let snackBarMock: SpyObj<MatSnackBar>;

  const userObj = {
    id: 1,
    email: 'test@hr.nl',
    firstName: 'Firstname',
    lastName: 'Lastname',
    role: 'ROLE_ADMIN'
  };

  beforeEach(() => {
    wishlistServiceMock = createSpyObj(['create', 'findByUser', 'update', 'delete']);
    snackBarMock = createSpyObj(['open']);
    component = new WishlistComponent(wishlistServiceMock, snackBarMock);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should find a user when success', () => {
      const expectedUser: any = userObj;

      wishlistServiceMock.findByUser.and.returnValue(defer(() => Promise.resolve(expectedUser)));

      component.ngOnInit();
      expect(wishlistServiceMock.findByUser).toHaveBeenCalled();
    });

    it('should do nothing on failure', () => {
      const expectedUser: any = userObj;

      wishlistServiceMock.findByUser.and.returnValue(defer(() => Promise.reject({})));

      component.ngOnInit();
      expect(wishlistServiceMock.findByUser).toHaveBeenCalled();
    });
  });

  describe('deleteWishlist', () => {
    it('should delete a wishlist on success', () => {
      const expectedUser: any = userObj;

      wishlistServiceMock.delete.and.returnValue(defer(() => Promise.resolve()));
      wishlistServiceMock.findByUser.and.returnValue(defer(() => Promise.resolve(expectedUser)));

      component.deleteWishlist(1);
      expect(wishlistServiceMock.delete).toHaveBeenCalled();
    });

    it('should do nothing on failure', () => {
      const expectedUser: any = userObj;

      wishlistServiceMock.delete.and.returnValue(defer(() => Promise.reject()));


      component.deleteWishlist(1);
      expect(wishlistServiceMock.delete).toHaveBeenCalled();
    });
  });

  describe('deleteEventFromWishlist', () => {
    it('should delete event from a wishlist on success', () => {
      const expectedUser: any = userObj;
      const eventId = 1;
      const wishlistObj = {
        events: []
      };

      wishlistServiceMock.update.and.returnValue(defer(() => Promise.resolve({})));
      wishlistServiceMock.findByUser.and.returnValue(defer(() => Promise.resolve(expectedUser)));
      component.deleteEventFromWishlist(wishlistObj, eventId);
      expect(wishlistServiceMock.update).toHaveBeenCalled();
    });

    it('should do nothing on failure', () => {
      const eventId = 1;
      const wishlistObj = {
        events: []
      };

      wishlistServiceMock.update.and.returnValue(defer(() => Promise.reject({})));
      component.deleteEventFromWishlist(wishlistObj, eventId);
      expect(wishlistServiceMock.update).toHaveBeenCalled();
    });
  });

  describe('onSubmit', () => {
    it('should create a wishlist on success', () => {
      const expectedUser: any = userObj;
      wishlistServiceMock.create.and.returnValue(defer(() => Promise.resolve({})));
      wishlistServiceMock.findByUser.and.returnValue(defer(() => Promise.resolve(expectedUser)));
      component.onSubmit();
      expect(wishlistServiceMock.create).toHaveBeenCalled();
    });

    it('should do nothing on failure', () => {
      wishlistServiceMock.create.and.returnValue(defer(() => Promise.reject({})));
      component.onSubmit();
      expect(wishlistServiceMock.create).toHaveBeenCalled();
    });
  });

  describe('onSubmitEditForm', () => {
    it('should update a wishlist on success', () => {
      const expectedUser: any = userObj;
      const wishlistObj = {
        id: 1,
        events: []
      };
      wishlistServiceMock.update.and.returnValue(defer(() => Promise.resolve({})));
      wishlistServiceMock.findByUser.and.returnValue(defer(() => Promise.resolve(expectedUser)));
      component.onSubmitEditForm(wishlistObj);
      expect(wishlistServiceMock.update).toHaveBeenCalled();
    });

    it('should do nothing on failure', () => {
      const wishlistObj = {
        id: 1,
        events: []
      };
      wishlistServiceMock.update.and.returnValue(defer(() => Promise.reject({})));
      component.onSubmitEditForm(wishlistObj);
      expect(wishlistServiceMock.update).toHaveBeenCalled();
    });
  });
});
