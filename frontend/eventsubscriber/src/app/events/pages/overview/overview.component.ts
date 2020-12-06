import { Component, OnInit } from '@angular/core';
import { EventsService } from '../../services';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  public events: any;

  constructor(
    private eventsService: EventsService
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

}
