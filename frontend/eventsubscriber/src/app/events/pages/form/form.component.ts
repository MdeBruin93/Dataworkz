import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { EventsService } from '../../services';
import { Event } from '../../models';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {
  eventId: any;
  eventCreateForm: FormGroup = Event.getFormGroup();
  file: any;

  constructor(
    private eventService: EventsService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.eventId = this.route.snapshot.paramMap.get('eventId');
    if (this.eventId) {
      this.eventService.findById(this.eventId).subscribe({
        next: response => {
          this.eventCreateForm.patchValue(response)
        },
        error: error => {
          this.snackBar.open('Failed to open an event');
          console.error('There was an error!', error);
        }
      });
    } else {
      this.eventCreateForm = Event.getFormGroup(true);
    }
  }

  onSubmit() {
    var imageUploadFormData: any = new FormData();
    if (this.file) {
      imageUploadFormData.append("file", this.file);
    }

    let eventData = this.eventCreateForm.value;
    delete eventData.image;

    if (this.eventId) {
      eventData.id = this.eventId;
    }

    this.eventService.save(eventData, imageUploadFormData).subscribe({
      next: _response => {
        this.snackBar.open('Event successfully saved');
        this.router.navigate(['/events']);
      },
      error: error => {
        this.eventCreateForm.reset();
        this.snackBar.open('Failed to save your event');
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
