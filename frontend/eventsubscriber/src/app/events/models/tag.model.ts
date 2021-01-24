import { FormControl, FormGroup, Validators } from "@angular/forms";
import { IUser } from "@core/models";

export interface ITag {
    id?: number;
    name: string;
}

export class Tag {
    static getFormGroup() {
      return new FormGroup({
        id: new FormControl('', []),
        name: new FormControl('', [Validators.required])
      });
    }
}
