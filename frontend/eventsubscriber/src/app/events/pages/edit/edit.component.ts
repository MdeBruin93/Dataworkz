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
    this.eventService.findById(this.eventId).subscribe({
      next: response => {
        this.eventCreateForm.patchValue(response)
      },
      error: error => {
        this.snackBar.open('Failed to open an event');
        console.error('There was an error!', error);
      }
    });
  }

  onSubmit() {
    var imageUploadFormData: any = new FormData();
    if (this.file) {
      imageUploadFormData.append("file", this.file);
    }

    let eventData = this.eventCreateForm.value;
    delete eventData.image;

    this.eventService.update(this.eventId, eventData, imageUploadFormData).subscribe({
        next: _response => {
          this.snackBar.open('Event successfully modified');
          this.router.navigate(['/events']);
        },
        error: error => {
          this.eventCreateForm.reset();
          this.snackBar.open('Failed to modify your event');
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
