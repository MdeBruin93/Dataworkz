import { ComponentFixture, TestBed } from '@angular/core/testing';
import {ActivatedRoute, Router} from '@angular/router';
import { EventsService } from '../../services';

import { DetailComponent } from './detail.component';
import {HttpClient} from '@angular/common/http';
import {UsersService} from '@core/services';
import SpyObj = jasmine.SpyObj;
import {AuthService} from '@auth/services';
import createSpyObj = jasmine.createSpyObj;
import {IEvent, IEventResponse} from '../../models';
import {Category} from '@core/models';
import {defer} from "rxjs";

describe('DetailComponent', () => {
  let component: DetailComponent;
  let eventServiceMock: SpyObj<EventsService>;
  let routerMock: SpyObj<ActivatedRoute>;

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
    routerMock = createSpyObj('ActivatedRoute', ['toStrings', 'navigate'], {snapshot: {params: {id: undefined}}});
    eventServiceMock = createSpyObj('EventService', ['subscribe', 'findById', 'delete']);
    component = new DetailComponent(eventServiceMock, routerMock);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should do nothing when id is empty/undefined', () => {
      component.ngOnInit();
      expect(eventServiceMock.findById).not.toHaveBeenCalled();
    });

    it('should find an event', () => {
      const expectedEvent: IEventResponse = eventObject;
      // @ts-ignore
      Object.getOwnPropertyDescriptor(routerMock, 'snapshot').get.and.returnValue({params: {id: eventObject.id}});
      eventServiceMock.findById.withArgs(eventObject.id)
        .and.returnValue(defer(() => Promise.resolve(expectedEvent)));

      component.ngOnInit();
      expect(eventServiceMock.findById).toHaveBeenCalled();
    });

    it('should not find an event', () => {
      const expectedEvent: IEventResponse = eventObject;
      // @ts-ignore
      Object.getOwnPropertyDescriptor(routerMock, 'snapshot').get.and.returnValue({params: {id: eventObject.id}});
      eventServiceMock.findById.withArgs(eventObject.id)
        .and.returnValue(defer(() => Promise.reject()));

      component.ngOnInit();
      expect(eventServiceMock.findById).toHaveBeenCalled();
    });
  });

  describe('subscribeToEvent', () => {
    it('should do next when success', () => {
      const expectedEvent: IEventResponse = eventObject;

      eventServiceMock.subscribe.and.returnValue(defer(() => Promise.resolve(expectedEvent)));

      component.subscribeToEvent(expectedEvent);
      expect(eventServiceMock.subscribe).toHaveBeenCalledWith(expectedEvent.id);
    });

    it('should do error when failed', () => {
      const expectedEvent: IEventResponse = eventObject;

      eventServiceMock.subscribe.and.returnValue(defer(() => Promise.reject()));

      component.subscribeToEvent(expectedEvent);
      expect(eventServiceMock.subscribe).toHaveBeenCalledWith(expectedEvent.id);
    });
  });

  describe('deleteEvent', () => {
    it('should do next when success', () => {
      eventServiceMock.delete.and.returnValue(defer(() => Promise.resolve()));

      component.deleteEvent(1);
      expect(eventServiceMock.delete).toHaveBeenCalledWith(1);
    });

    it('should do error when failed', () => {
      eventServiceMock.delete.and.returnValue(defer(() => Promise.reject()));

      component.deleteEvent(1);
      expect(eventServiceMock.delete).toHaveBeenCalledWith(1);
    });
  });
});
