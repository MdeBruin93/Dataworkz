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

  it(`test storeEventImage()`, async(inject([HttpTestingController, EventsService],
    (httpClient: HttpTestingController, eventsService: EventsService) => {
      eventsService
        .storeEventImage(formData)
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/storage/upload');
      expect(req.request.method).toBe("POST");
      httpMock.verify();
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

  it(`test findByUser()`, async(inject([HttpTestingController, EventsService],
    (httpClient: HttpTestingController, eventsService: EventsService) => {
      eventsService
        .findByUser()
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/events/findbyuser');
      expect(req.request.method).toBe("GET");
      httpMock.verify();
    }))
  );

  it(`test findById()`, async(inject([HttpTestingController, EventsService],
    (httpClient: HttpTestingController, eventsService: EventsService) => {
      eventsService
        .findById(1)
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/events/1');
      expect(req.request.method).toBe("GET");
      httpMock.verify();
    }))
  );

  it(`test subscribe()`, async(inject([HttpTestingController, EventsService],
    (httpClient: HttpTestingController, eventsService: EventsService) => {
      eventsService
        .subscribe(1)
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/events/1/subscribe');
      expect(req.request.method).toBe("POST");
      httpMock.verify();
    }))
  );

  it(`test delete()`, async(inject([HttpTestingController, EventsService],
    (httpClient: HttpTestingController, eventsService: EventsService) => {
      eventsService
        .delete(1)
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/events/1');
      expect(req.request.method).toBe("DELETE");
      httpMock.verify();
    }))
  );
});
