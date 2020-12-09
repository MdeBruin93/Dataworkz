import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { EventsService } from '../../services';
import { Event } from '../../models';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
  eventId: any;
  event: any;
  eventCreateForm: FormGroup = Event.getFormGroup();

  constructor(
    private eventService: EventsService,
    private route: ActivatedRoute,
    private router: Router,
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

  onSubmit() {
    this.eventService.update(this.eventId, this.eventCreateForm.value).subscribe({
        next: _response => {
          this.snackBar.open('Event successfully edited');
          this.router.navigate(['/events']);
        },
        error: error => {
          this.eventCreateForm.reset();
          this.snackBar.open('Failed to edit your event');
          console.error('There was an error!', error);
        }
    });
  }

}
