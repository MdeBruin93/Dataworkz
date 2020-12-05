import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { EventsService } from '../../services';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {
  eventCreateForm: FormGroup = new FormGroup({
    title: new FormControl('', [
      Validators.required,
      Validators.maxLength(100)
    ]),
    description: new FormControl('', [Validators.required]),
    date: new FormControl('', [
      Validators.required,
    ]),
    maxAmountOfAttendees: new FormControl('', [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(1),
    ]),
    euroAmount: new FormControl('', [
      Validators.required,
      Validators.pattern("^[0-9]*$"),
      Validators.min(0),
    ])
  });

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
