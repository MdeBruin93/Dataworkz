import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from '@auth/services';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  token: string = '';
  newPasswordHide = true;
  repeatNewPasswordHide = true;
  resetPasswordForm: FormGroup = new FormGroup({
    newPassword: new FormControl('', [
      Validators.required
    ]),
    repeatNewPassword: new FormControl('', [
      Validators.required
    ]),
  });

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.token = this.route.snapshot.params.token;
  }

  isPasswordsEqual(): boolean {
    return this.resetPasswordForm.value.newPassword == this.resetPasswordForm.value.repeatNewPassword;
  }

  onSubmit() {
    let resetPasswordData = this.resetPasswordForm.value;
    resetPasswordData['token'] = `${this.token}`;
    this.authService.resetPassword(resetPasswordData).subscribe(
      (_response) => {
        this.router.navigate(['./login']);
      }
    );
  }

}
