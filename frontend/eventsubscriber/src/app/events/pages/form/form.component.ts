import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { ActivatedRoute, Router } from '@angular/router';
import { EventsService } from '../../services';
import { IEvent, Event, IEventResponse } from '../../models';

import { Store, Select} from '@ngxs/store';
import { CategoriesState, LoadCategories } from '@core/store';
import { Observable } from 'rxjs';
import { Category } from '@core/models';
import {map, startWith} from 'rxjs/operators';
import {MatChipInputEvent} from '@angular/material/chips';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {
  @Select(CategoriesState.categories)
  public categories$: Observable<any>;

  eventId: any;
  eventCreateForm: FormGroup = Event.getFormGroup();
  file: any;
  currentEvent: Event;

  tagCtrl = new FormControl();
  filteredTags: Observable<string[]>;
  tags: string[] = ['Rotterdam'];
  allTags: string[] = ['Rotterdam', 'Sport', 'Tech'];
  availableTags: string[] = [];

  @ViewChild('tagInput') tagInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(
    private eventService: EventsService,
    private route: ActivatedRoute,
    private router: Router,
    private snackBar: MatSnackBar,
    private store: Store
  ) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadCategories());
    this.eventId = this.route.snapshot.paramMap.get('eventId');
    if (this.eventId) {
      this.eventService.findById(this.eventId).subscribe({
        next: (response: any) => {
          this.eventCreateForm.patchValue(response);
          this.eventCreateForm.get('categoryId')?.setValue(response.category.id);
        },
        error: error => {
          this.snackBar.open('Failed to open an event');
          console.error('There was an error!', error);
        }
      });
    } else {
      this.eventCreateForm = Event.getFormGroup(true);
    }

    this.availableTags = this.allTags.filter((t) => !this.tags.includes(t));
    this.filteredTags = this.tagCtrl.valueChanges.pipe(
        startWith(null),
        map((tag: string | null) => {
          console.log(tag);
          return tag ? this._filter(tag) : this.availableTags.slice()
        }));
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

    eventData.category = {id: eventData.categoryId};

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

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    console.log(value);

    if ((value || '').trim()) {
      this.tags.push(value.trim());
      this.tagInput.nativeElement.value = '';
    }

    this.tagCtrl.setValue(null);
  }

  removeTag(tag: string): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }

    this.availableTags = this.allTags.filter((t) => !this.tags.includes(t));
    this.tagCtrl.setValue(null);
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.tags.push(event.option.viewValue);
    this.availableTags = this.allTags.filter((t) => !this.tags.includes(t));
    this.tagInput.nativeElement.value = '';
    this.tagCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.availableTags.filter(tag => tag.toLowerCase().indexOf(filterValue) === 0);
  }

}
