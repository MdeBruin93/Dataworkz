import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EventsService } from '../../services';
import { IEventResponse } from '../../models/event.model';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  public events: any;

  constructor(
    private eventsService: EventsService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.eventsService.getAll().subscribe({
      next: _response => {
        console.log(_response);
        this.events = _response;
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

  navigateToDetail(event: IEventResponse) {
    this.router.navigate(['/events', event.id]);
  }

  subscribeToEvent(id: number, event: any) {
    event.stopPropagation();
    event.preventDefault();
    this.eventsService.subscribe(id).subscribe({
      next: _response => {
        console.log(_response);
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

  sanitize(url: string) {
    return this.eventsService.sanitize(url);
  }
}
