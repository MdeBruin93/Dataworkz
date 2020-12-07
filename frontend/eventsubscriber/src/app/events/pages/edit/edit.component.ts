import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { IEventResponse } from '../../models';
import { EventsService } from '../../services';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
  eventId: any;
  event: any;

  constructor(
    private eventService: EventsService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.eventId = this.route.snapshot.paramMap.get('eventId');
    this.eventService.getOneWithId(this.eventId).subscribe({
      next: response => {
        this.event = response;
        console.log(this.event);
      },
      error: error => {
        this.snackBar.open('Failed to open an event');
        console.error('There was an error!', error);
      }
  });
  }

}
