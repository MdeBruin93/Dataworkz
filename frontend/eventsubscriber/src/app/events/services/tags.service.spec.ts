import { async, inject, TestBed } from '@angular/core/testing';
import SpyObj = jasmine.SpyObj;
import createSpyObj = jasmine.createSpyObj;

import { TagsService } from './tags.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { environment } from '@environments/environment';
import { defer } from 'rxjs';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('TagsService', () => {
  let service: TagsService;
  let httpClientMock: SpyObj<HttpClient>;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        HttpClientModule
      ], 
    });

    service = TestBed.inject(TagsService);
    httpMock = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it(`test create`, async(inject([HttpTestingController, TagsService],
    (httpClient: HttpTestingController, tagsService: TagsService) => {
      tagsService
        .getAll()
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/tags/');
      expect(req.request.method).toBe("GET");
      httpMock.verify();
    }))
  );

  it(`test create`, async(inject([HttpTestingController, TagsService],
    (httpClient: HttpTestingController, tagsService: TagsService) => {
      tagsService
        .create({id: 1, name: 'test'})
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/tags/');
      expect(req.request.method).toBe("POST");
      httpMock.verify();
    }))
  );
});
