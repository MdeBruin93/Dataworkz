import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { WishlistService } from 'src/app/wishlists';
import { EventsService, UserService } from '../../services';

import { OverviewComponent } from './overview.component';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;
import {defer} from 'rxjs';
import {IEventResponse} from '../../models';

describe('OverviewComponent', () => {
  let component: OverviewComponent;
  let eventsServiceMock: SpyObj<EventsService>;
  let wishlistServiceMock: SpyObj<WishlistService>;
  let userServiceMock: SpyObj<UserService>;
  let routerMock: SpyObj<Router>;
  let storeMock: SpyObj<Store>;
  let dialogMock: SpyObj<MatDialog>;

  const eventObject = {
    id: 1,
    title: 'Test',
    description: 'Test',
    date: new Date(),
    maxAmountOfAttendees: 1,
    euroAmount: 1,
    category: {
      id: 1,
      name: 'Category',
      color: 'Red'
    },
    imageUrl: 'localhost:8080',
    user: {
      id: 1,
      email: 'test@hr.nl',
      firstName: 'Firstname',
      lastName: 'Lastname',
      role: 'ROLE_ADMIN'
    },
    categoryId: 1
  };

  beforeEach(() => {
    eventsServiceMock = createSpyObj('EventsService', ['getAll', 'subscribe', 'delete', 'sanitize']);
    wishlistServiceMock = createSpyObj('WishlistService', ['']);
    userServiceMock = createSpyObj('UserService', ['']);
    routerMock = createSpyObj('Router', ['navigate']);
    storeMock = createSpyObj('Store', ['dispatch']);
    dialogMock = createSpyObj('MatDialog', ['']);
    component = new OverviewComponent(
      eventsServiceMock,
      wishlistServiceMock,
      userServiceMock,
      routerMock,
      storeMock,
      dialogMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // describe('ngOnInit', () => {
  //   it('should get all event when success', () => {
  //     const expectedEvents: IEventResponse[] = [eventObject];
  //
  //     eventsServiceMock.getAll.and.returnValue(defer(() => Promise.resolve(expectedEvents)));
  //
  //     component.ngOnInit();
  //     expect(storeMock.dispatch).toHaveBeenCalled();
  //     expect(eventsServiceMock.getAll).toHaveBeenCalled();
  //   });
  // });

  describe('navigateToDetail', () => {
    it('should navigate te events', () => {
      const expectedEvent: IEventResponse = eventObject;

      component.navigateToDetail(expectedEvent);
      expect(routerMock.navigate).toHaveBeenCalledWith(['/events', expectedEvent.id]);
    });
  });

  describe('subscribeToEvent', () => {
    it('should subscribe to event', () => {
      const eventId = 1;
      const event: any = createSpyObj(['stopPropagation', 'preventDefault']);
      const expectedEvent: IEventResponse = eventObject;

      eventsServiceMock.subscribe.and.returnValue(defer(() => Promise.resolve(expectedEvent)));

      component.subscribeToEvent(eventId, event);
      expect(event.stopPropagation).toHaveBeenCalled();
      expect(event.preventDefault).toHaveBeenCalled();
    });

    it('should fail when error', () => {
      const eventId = 1;
      const event: any = createSpyObj(['stopPropagation', 'preventDefault']);
      const expectedEvent: IEventResponse = eventObject;

      eventsServiceMock.subscribe.and.returnValue(defer(() => Promise.reject()));

      component.subscribeToEvent(eventId, event);
      expect(event.stopPropagation).toHaveBeenCalled();
      expect(event.preventDefault).toHaveBeenCalled();
    });
  });

  describe('getFormData', () => {
    it('should return formdata when success', () => {
      const result = component.getFormData({event: 'Test'});
      expect(result.get('event')).toEqual('Test');
    });
  });

  describe('deleteEvent', () => {
    it('should fail when error', () => {
      eventsServiceMock.delete.and.returnValue(defer(() => Promise.reject()));

      component.deleteEvent(1);
      expect(eventsServiceMock.delete).toHaveBeenCalled();
    });
  });

  describe('sanitize', () => {
    it('should sanitize', () => {
      const expectedValue = 'Hello world!';
      eventsServiceMock.sanitize.and.returnValue(expectedValue);

      const result = component.sanitize(expectedValue);
      expect(result).toEqual(expectedValue);
    });
  })

  describe('selectCategory', () => {
    it('should selectCategory', () => {
      component.events = ['1', '2'];
      component.categoriesFormControl.setValue([]);
      console.log(component.categoriesFormControl.value.length);
      

      expect(component.filteredEvents).toEqual(undefined);
    });
  })
});
