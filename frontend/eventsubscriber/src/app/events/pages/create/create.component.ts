import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { EventsService } from '../../services';
import { Event } from '../../models';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {
  eventCreateForm: FormGroup = Event.getFormGroup();

  constructor(
    private eventService: EventsService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.eventService.create(this.eventCreateForm.value).subscribe({
        next: _response => {
          this.snackBar.open('Event successfully created');
          this.router.navigate(['/events']);
        },
        error: error => {
          this.eventCreateForm.reset();
          this.snackBar.open('Failed to create your event');
          console.error('There was an error!', error);
        }
    });
  }

}
