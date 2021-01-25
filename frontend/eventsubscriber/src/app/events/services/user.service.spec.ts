import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { async, inject, TestBed } from '@angular/core/testing';

import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        UserService,
        HttpClientModule
      ],
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.get(HttpTestingController);
  });

  it(`test create`, async(inject([HttpTestingController, UserService],
    (httpClient: HttpTestingController, userService: UserService) => {
      userService
        .subscribedToEvents()
        .subscribe((_response: any) => {});

      let req = httpMock.expectOne('http://localhost:8080/api/users/subscriptions');
      expect(req.request.method).toBe("GET");
      httpMock.verify();
    }))
  );
});
