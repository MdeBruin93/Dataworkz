import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventsService } from '../../services/events.service';
import { IEventResponse } from '../../models/event.model';


@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {
  public event: any;

  constructor(
    private eventsService: EventsService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.params.id;
    if (id) {
      this.eventsService.findById(id).subscribe({
        next: _response => {
          console.log(_response);
          this.event = _response;
        },
        error: error => {
          console.error('There was an error!', error);
        }
      });
    }
  }

  subscribeToEvent(event: IEventResponse) {
    this.eventsService.subscribe(event.id).subscribe({
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
