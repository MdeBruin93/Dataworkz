import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    email: new FormControl('', [
      Validators.required,
      Validators.email
    ]),
    password: new FormControl('', [
      Validators.required
    ])
  });
  passwordHide: boolean = true;

  constructor(
    private http: HttpClient,
    private snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {

  }

  onSubmit() {
    //TODO: add model
    this.http.post<any>('http://localhost:8080/api/auth/register', this.registerForm.value).subscribe({
        next: _response => {
          this.snackBar.open('Registration Successfull');
        },
        error: error => {
          this.snackBar.open('Registration Failed');
          console.error('There was an error!', error);
        }
    });
  }

}
