import { Component, OnInit } from '@angular/core';
import { Category } from '@core/models';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {
  public categoryForm = Category.getFormGroup();

  constructor() { }

  ngOnInit(): void {
  }


}
