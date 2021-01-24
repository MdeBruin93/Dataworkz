import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IUser, Category } from '@core/models';
import { ITag } from './tag.model';

export interface IEvent {
    title: string;
    description: string;
    date: Date;
    maxAmountOfAttendees: number;
    euroAmount: number;
    imageUrl: string;
    category: Category;
}

export interface IEventResponse extends IEvent {
    id: number;
    imageUrl: string;
    user: IUser;
    categoryId: number;
    tags: ITag[];
}

export interface IFileResponse {
  fileUrl: string;
}

export class Event {
  static getFormGroup(isImageRequired = false) {
    return new FormGroup({
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
      ]),
      image: new FormControl(null, isImageRequired ? [Validators.required] : []),
      imageUrl: new FormControl('', []),
      categoryId: new FormControl('', [Validators.required])
    });
  }
}
