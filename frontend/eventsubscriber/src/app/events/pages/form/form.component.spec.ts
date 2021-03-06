import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngxs/store';
import {EventsService, TagsService} from '../../services';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;

import { FormComponent } from './form.component';
import { IEventResponse } from '../../models';
import { defer } from 'rxjs';
import Spy = jasmine.Spy;

describe('FormComponent', () => {
  let component: FormComponent;
  let activateRouteMock: SpyObj<ActivatedRoute>;
  let routerMock: SpyObj<Router>;
  let snackBarMock: SpyObj<MatSnackBar>;
  let storeMock: SpyObj<Store>;
  let eventServiceMock: SpyObj<EventsService>;
  let tagsServiceMock: SpyObj<TagsService>;

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
      color: 'Red',
      endDate: undefined,
      deleted: false
    },
    imageUrl: 'localhost:8080',
    user: {
      id: 1,
      email: 'test@hr.nl',
      firstName: 'Firstname',
      lastName: 'Lastname',
      role: 'ROLE_ADMIN'
    },
    categoryId: 1,
    tags: [
      {
        id: 1,
        name: 'string'
      }
    ]
  };

  const event: IEventResponse = {
    id: 1,
    title: 'Test',
    description: 'Test',
    date: new Date(),
    maxAmountOfAttendees: 1,
    euroAmount: 1,
    category: {
      id: 1,
      name: 'Category',
      color: 'Red',
      endDate: undefined,
      deleted: false
    },
    imageUrl: 'localhost:8080',
    user: {
      id: 1,
      email: 'test@hr.nl',
      firstName: 'Firstname',
      lastName: 'Lastname',
      role: 'ROLE_ADMIN'
    },
    categoryId: 1,
    tags: [
      {
        id: 1,
        name: 'string'
      }
    ]
  };

  beforeEach(() => {
    storeMock = createSpyObj('Store', ['dispatch']);
    snackBarMock = createSpyObj('MatSnackBar', ['open']);
    eventServiceMock = createSpyObj('EventService', ['subscribe', 'findById', 'save']);
    activateRouteMock = createSpyObj('ActivatedRoute', ['toStrings', 'navigate'], {snapshot: {params: {eventId: undefined}}});
    routerMock = createSpyObj('Router', ['navigate']);
    tagsServiceMock = createSpyObj('TagService', ['getAll']);
    component = new FormComponent(
      eventServiceMock,
      activateRouteMock,
      routerMock,
      snackBarMock,
      storeMock,
      tagsServiceMock
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  xdescribe('ngOnInit()', () => {
    it('should do nothing when id is empty/undefined', () => {
      component.ngOnInit();
      expect(eventServiceMock.findById).not.toHaveBeenCalled();
    });

    it('should find an event', () => {
      const expectedEvent: IEventResponse = eventObject;
      // @ts-ignore
      Object.getOwnPropertyDescriptor(activateRouteMock, 'snapshot').get.and.returnValue({params: {eventId: eventObject.id}});
      eventServiceMock.findById.withArgs(eventObject.id)
        .and.returnValue(defer(() => Promise.resolve(expectedEvent)));

      component.ngOnInit();
      expect(eventServiceMock.findById).toHaveBeenCalled();
    });

    it('should not find an event', () => {
      // @ts-ignore
      Object.getOwnPropertyDescriptor(activateRouteMock, 'snapshot').get.and.returnValue({params: {eventId: eventObject.id}});
      eventServiceMock.findById.withArgs(eventObject.id)
        .and.returnValue(defer(() => Promise.reject()));
      tagsServiceMock.getAll.and.returnValue(defer(() => Promise.reject()));

      component.ngOnInit();
      expect(eventServiceMock.findById).toHaveBeenCalled();
    });
  });

  describe('onSubmit()', () => {
    it('should save an event', () => {
      const expectedEvent: IEventResponse = eventObject;
      eventServiceMock.save.and.returnValue(defer(() => Promise.resolve(expectedEvent)));
      eventServiceMock.save.and.returnValue(defer(() => Promise.resolve(expectedEvent)));


      component.onSubmit();
      expect(eventServiceMock.save).toHaveBeenCalled();
    });

    it('should not save an event', () => {
      eventServiceMock.save.and.returnValue(defer(() => Promise.reject()));

      component.onSubmit();
      expect(eventServiceMock.save).toHaveBeenCalled();
    });

    describe('onSelect()', () => {
      it('should select a file', () => {
        component.onSelect({event: {target: {files: {0: 'test'}}}});
        expect(component.file).toEqual(undefined);
      });
    });
  });
});
