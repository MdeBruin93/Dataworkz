import {ICategory} from '@core/interfaces';
import { FormControl, FormGroup, Validators } from '@angular/forms';

export class Category implements ICategory {
  public id: number | undefined;
  public name: string;
  public color: string;

  constructor(name: string, color: string, id?: number) {
    this.id = id;
    this.name = name;
    this.color = color;
  }

  static getFormGroup() {
    return new FormGroup({
      id: new FormControl(),
      name: new FormControl('', [
        Validators.required,
        Validators.maxLength(100)
      ])
    });
  }
}
