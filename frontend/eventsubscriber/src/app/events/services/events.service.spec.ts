import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { async, inject, TestBed } from '@angular/core/testing';
import { DomSanitizer } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { IEventResponse } from '../models';

import { EventsService } from './events.service';

describe('EventsService', () => {
  let service: EventsService;
  let httpMock: HttpTestingController;
  let spy: any;

  let event: IEventResponse = {
    id: 1,
    title: 'string',
    description: 'string',
    date: new Date(Date.now()),
    imageUrl: 'string',
    user: {
      id: 1,
      email: 'string',
      role: 'string',
      firstName: 'string',
      lastName: 'string',
    },
    maxAmountOfAttendees: 10,
    euroAmount: 10,
    category: {
      id: 1,
      name: 'string',
      color: 'string'
    },
    categoryId: 1
  }

  const formData: any = new FormData();

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        EventsService,
        HttpClientModule,
        DomSanitizer
      ],
    });
    service = TestBed.inject(EventsService);
    httpMock = TestBed.get(HttpTestingController);
  });

  it(`test save(), create flow`, async(inject([HttpTestingController, EventsService],
    (httpClient: HttpTestingController, eventsService: EventsService) => {
      event.id = undefined;
      spy = spyOn(eventsService, 'create').and.callThrough()
      eventsService.save(event, formData);
      expect(spy).toHaveBeenCalled();
    }))
  );

  it(`test save(), update flow`, async(inject([HttpTestingController, EventsService],
    (httpClient: HttpTestingController, eventsService: EventsService) => {
      event.id = 1;
      spy = spyOn(eventsService, 'update').and.callThrough();
      eventsService.save(event, formData);
      expect(spy).toHaveBeenCalled();
    }))
  );
  
  it(`test create`, async(inject([HttpTestingController, EventsService],
    (httpClient: HttpTestingController, eventsService: EventsService) => {
      eventsService
        .getAll()
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/events/');
      expect(req.request.method).toBe("GET");
      httpMock.verify();
    }))
  );
});
