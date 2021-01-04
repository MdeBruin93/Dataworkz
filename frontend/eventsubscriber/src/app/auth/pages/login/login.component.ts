import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Store } from '@ngxs/store';
import { Login } from '@core/store';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup = new FormGroup({
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
    private store: Store,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.store.dispatch(new Login(this.loginForm.value.email, this.loginForm.value.password)).subscribe(
    (response) => {
      if (response.auth.token && response.auth.currentUser) {
        this.snackBar.open('Login succeeded');
        this.router.navigate(['/events']);
      };
    },
    (error) => {
      this.loginForm.reset();
      this.snackBar.open('Login failed');
      console.error('There was an error!', error);
    });
  }
}