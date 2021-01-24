import { async, inject, TestBed } from '@angular/core/testing';

import { QaService } from './qa.service';

import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';

describe('QaService', () => {
  let service: QaService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        QaService,
        HttpClientModule
      ],
    });

    service = TestBed.inject(QaService);
    httpMock = TestBed.get(HttpTestingController);
  });

  it(`test create`, async(inject([HttpTestingController, QaService],
    (httpClient: HttpTestingController, qaService: QaService) => {
      qaService
        .create([])
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/questions/');
      expect(req.request.method).toBe("POST");
      httpMock.verify();
    }))
  );

  it(`test update`, async(inject([HttpTestingController, QaService],
    (httpClient: HttpTestingController, qaService: QaService) => {
      qaService
        .update(1,[])
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/questions/1');
      expect(req.request.method).toBe("PUT");
      httpMock.verify();
    }))
  );

  it(`test delete`, async(inject([HttpTestingController, QaService],
    (httpClient: HttpTestingController, qaService: QaService) => {
      qaService
        .delete(1)
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/questions/1');
      expect(req.request.method).toBe("DELETE");
      httpMock.verify();
    }))
  );

  it(`test createAnswer`, async(inject([HttpTestingController, QaService],
    (httpClient: HttpTestingController, qaService: QaService) => {
      qaService
        .createAnswer([])
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/answers/');
      expect(req.request.method).toBe("POST");
      httpMock.verify();
    }))
  );

  it(`test deleteAnswer`, async(inject([HttpTestingController, QaService],
    (httpClient: HttpTestingController, qaService: QaService) => {
      qaService
        .deleteAnswer(1)
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/answers/1');
      expect(req.request.method).toBe("DELETE");
      httpMock.verify();
    }))
  );

  it(`test updateAnswer`, async(inject([HttpTestingController, QaService],
    (httpClient: HttpTestingController, qaService: QaService) => {
      qaService
        .updateAnswer(1,[])
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/answers/1');
      expect(req.request.method).toBe("PUT");
      httpMock.verify();
    }))
  );
});
