import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { DomSanitizer } from '@angular/platform-browser';

import { EventsService } from './events.service';

describe('EventsService', () => {
  let service: EventsService;
  let http: HttpClient;
  let sanitizer: DomSanitizer;

  beforeEach(() => {
    service = new EventsService(
      http,
      sanitizer
    );
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
