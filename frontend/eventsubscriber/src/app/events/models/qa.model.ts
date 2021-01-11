import { FormControl, FormGroup, Validators } from "@angular/forms";

export interface IQuestion {
    id: number;
    text: string;
    eventId: number;
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