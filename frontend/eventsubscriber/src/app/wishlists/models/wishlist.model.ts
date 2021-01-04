import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IEvent } from 'src/app/events/models';

export interface IWishlist {
    id: number;
    version: number;
    created: Date;
    lastModified: Date;
    name: string;
    events: IEvent[];
}

export class Wishlist {
  static getFormGroup() {
    return new FormGroup({
      name: new FormControl('', [
        Validators.required,
        Validators.maxLength(100)
      ])
    });
  }
}