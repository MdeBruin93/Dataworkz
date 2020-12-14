import { FormControl, FormGroup, Validators } from '@angular/forms';
export interface IEvent {
    title: string;
    description: string;
    date: Date;
    maxAmountOfAttendees: number;
    euroAmount: number;
    image: string;
}

export interface IEventResponse extends IEvent {
    id: number;
    imageUrl: string;
}

export class Event {
  static getFormGroup() {
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
      image: new FormControl(null, [
        Validators.required,
      ])
    });
  }
}
