import {ICategory} from '@core/interfaces';
import { FormControl, FormGroup, Validators } from '@angular/forms';

export class Category implements ICategory {
  public id: number | undefined;
  public name: string;
  public color: string;
  public endDate: Date | undefined;
  public deleted: boolean;

  constructor(name: string, color: string, id?: number, endDate?: Date) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.endDate = endDate;
  }

  static getFormGroup() {
    return new FormGroup({
      id: new FormControl(),
      name: new FormControl('', [
        Validators.required,
        Validators.maxLength(100)
      ]),
      endDate: new FormControl(null, [])
    });
  }
}
