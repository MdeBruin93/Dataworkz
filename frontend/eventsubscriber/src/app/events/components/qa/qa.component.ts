import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Question, Answer } from '../../models';
import { QaService } from '../../services/qa.service';

import { Select } from '@ngxs/store';
import { AuthState } from '@core/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-qa',
  templateUrl: './qa.component.html',
  styleUrls: ['./qa.component.scss']
})
export class QaComponent implements OnInit {
  @Select(AuthState.isLoggedIn)
  public isLoggedIn$: Observable<boolean>;

  @Input() eventId: string;
  @Input() questions: any = [];
  @Output() reloadData = new EventEmitter<boolean>();
  reply: any = [];
  edit: any = [];
  replyAnswer: any = [];
  superiorAnswers: any = [];
  qaCreateform: FormGroup = Question.getFormGroup();
  qaEditForm: FormGroup = Question.getFormGroup();
  answerCreateForm: FormGroup = Answer.getFormGroup();
  answerEditForm: FormGroup = Answer.getFormGroup();

  constructor(
    private qaService: QaService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  deleteQuestion(questionId: number) {
    this.qaService.delete(questionId).subscribe({
      next: _response => {
        this.snackBar.open('Question successfully deleted');
        this.reloadData.emit(true);
      },
      error: error => {
        this.snackBar.open('Failed to delete your question');
        console.error('There was an error!', error);
      }
    });
  }

  deleteAnswer(answerId: number) {
    this.qaService.deleteAnswer(answerId).subscribe({
      next: _response => {
        this.snackBar.open('answer successfully deleted');
        this.reloadData.emit(true);
      },
      error: error => {
        this.snackBar.open('Failed to delete your answer');
        console.error('There was an error!', error);
      }
    });
  }

  onSubmit() {
    const question = this.qaCreateform.get('question') || {value: null};
    const data = {
      text: question.value,
      eventId: this.eventId
    }

    this.qaService.create(data).subscribe({
      next: _response => {
        this.snackBar.open('Question successfully posted');
        this.reloadData.emit(true);
        this.qaCreateform.reset();
      },
      error: error => {
        this.qaCreateform.reset();
        this.snackBar.open('Failed to post your question');
        console.error('There was an error!', error);
      }
    });
  }

  onSubmitEditForm(question: any) {
    const questionText = this.qaEditForm.get('question') || {value: null};
    const data = {
      text: questionText.value,
      eventId: this.eventId
    }

    this.qaService.update(question.id, data).subscribe({
      next: _response => {
        this.snackBar.open('Question successfully updated');
        this.reloadData.emit(true);
        this.edit = [];
        this.qaEditForm.reset();
      },
      error: error => {
        this.qaEditForm.reset();
        this.edit = [];
        this.snackBar.open('Failed to update your question');
        console.error('There was an error!', error);
      }
    });
  }

  onSubmitAnswer(question: any) {
    const answerText = this.answerCreateForm.get('answer') || {value: null};
    const data = {
      text: answerText.value,
      questionId: question.id
    }

    this.qaService.createAnswer(data).subscribe({
      next: _response => {
        this.snackBar.open('Question successfully updated');
        this.reloadData.emit(true);
        this.answerCreateForm.reset();
        this.reply = [];
      },
      error: error => {
        this.answerCreateForm.reset();
        this.reply = [];
        this.snackBar.open('Failed to update your question');
        console.error('There was an error!', error);
      }
    });
  }

  onSubmitEditAnswer(answer: any, question: any) {
    const answerText = this.answerEditForm.get('answer') || {value: null};
    const data = {
      text: answerText.value,
      questionId: question.id
    }

    this.qaService.updateAnswer(answer.id, data).subscribe({
      next: _response => {
        this.snackBar.open('answer successfully updated');
        this.reloadData.emit(true);
        this.replyAnswer = [];
        this.answerEditForm.reset();
      },
      error: error => {
        this.answerEditForm.reset();
        this.replyAnswer = [];
        this.snackBar.open('Failed to update your answer');
        console.error('There was an error!', error);
      }
    });
  }

}
