import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { DomSanitizer } from '@angular/platform-browser';
import { Event, IEventResponse } from '../models';

import { EventsService } from './events.service';
import { Observable, of } from 'rxjs';

describe('EventsService', () => {
  let service: EventsService;
  let http: HttpClient;
  let sanitizer: DomSanitizer;
  let httpClientSpy: { get: jasmine.Spy };
  let eventsService: EventsService

  beforeEach(() => {
    service = new EventsService(
      http,
      sanitizer
    );

    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    eventsService = new EventsService(httpClientSpy as any, sanitizer);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return expected events (HttpClient called once)', () => {
  const expectedEvents: IEventResponse[] =
    [
      {
        id: 1,
        title: 'A',
        imageUrl: 'test.jpg',
        description: 'test',
        date: new Date('January 21, 2021 23:15:30'),
        maxAmountOfAttendees: 12,
        euroAmount: 100,
        category: {
          id: 1,
          name: 'test',
          color: 'ffffff'
        },
        categoryId: 1,
        user: {
          id: 1,
          email: 'test@test.nl',
          role: 'admin',
          firstName: 'test',
          lastName: 'tester'
        }
      },
    ];

  httpClientSpy.get.and.returnValue(of(expectedEvents));

  eventsService.getAll().subscribe(
    events => expect(events).toEqual(expectedEvents, 'expected heroes'),
    fail
  );
  expect(httpClientSpy.get.calls.count()).toBe(1, 'one call');
});
});
