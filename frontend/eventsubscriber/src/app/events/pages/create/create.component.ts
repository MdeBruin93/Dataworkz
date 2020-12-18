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
  eventCreateForm: FormGroup = Event.getFormGroup(true);
  file: any;

  constructor(
    private eventService: EventsService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    var imageUploadFormData: any = new FormData();
    imageUploadFormData.append("file", this.file);

    let eventData = this.eventCreateForm.value;
    delete eventData.image;

    this.eventService.create(eventData, imageUploadFormData).subscribe({
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
