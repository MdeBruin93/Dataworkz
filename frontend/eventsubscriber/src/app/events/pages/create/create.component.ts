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
  file: any;

  constructor(
    private eventService: EventsService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    var formData: any = new FormData();
    const title = this.eventCreateForm.get('title') || {value: null};
    const date = this.eventCreateForm.get('date') || {value: null};
    const description = this.eventCreateForm.get('description') || {value: null};
    const maxAmountOfAttendees = this.eventCreateForm.get('maxAmountOfAttendees') || {value: null};
    const euroAmount = this.eventCreateForm.get('euroAmount') || {value: null};

    formData.append("title", title.value);
    formData.append("description", description.value);
    const dateValue = (date.value as Date);
    formData.append("date", dateValue.toISOString().split('T')[0]);
    formData.append("maxAmountOfAttendees", maxAmountOfAttendees.value);
    formData.append("euroAmount", euroAmount.value);
    formData.append("image", this.file);

    this.eventService.create(formData).subscribe({
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

  onSelect(event: any) {
    const eventObj = event || {target: null, };
    const target = eventObj.target || {files: []};
    this.file = target.files[0];
  }
}
