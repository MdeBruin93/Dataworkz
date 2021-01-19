import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { EventsService } from '../../services';

import { DetailComponent } from './detail.component';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let eventsService: EventsService;
  let route: ActivatedRoute;

  beforeEach(() => {
    component = new DetailComponent(
      eventsService,
      route
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
