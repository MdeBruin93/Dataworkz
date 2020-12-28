import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AuthService } from '@auth/services';

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
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {

  }

  onSubmit() {
    this.authService.register(this.registerForm.value).subscribe({
        next: _response => {
          this.router.navigate(['/login']);
          this.snackBar.open('Registration Successfull');
        },
        error: error => {
          this.registerForm.reset();
          this.snackBar.open('Registration Failed');
          console.error('There was an error!', error);
        }
    });
  }

}
