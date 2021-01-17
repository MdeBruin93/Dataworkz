import { FormControl, FormGroup, Validators } from "@angular/forms";
import { IUser } from "@core/models";

export interface IQuestion {
    id: number;
    text: string;
    eventId: number;
    owner: IUser;
}

export interface IAnswer {
  id: number;
  text: string;
  questionId: number;
  owner: IUser;
}

export class Question {
    static getFormGroup() {
      return new FormGroup({
        question: new FormControl('', [Validators.required]),
      });
    }
}

export class Answer {
    static getFormGroup() {
      return new FormGroup({
        answer: new FormControl('', [Validators.required]),
      });
    }
}
