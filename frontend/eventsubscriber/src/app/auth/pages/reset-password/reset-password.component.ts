import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { AuthService } from '@auth/services';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
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
    private authService: AuthService
  ) { }

  ngOnInit(): void {
  }

  isPasswordsEqual(): boolean {
    return this.resetPasswordForm.value.newPassword == this.resetPasswordForm.value.repeatNewPassword;
  }

  onSubmit() {
    this.authService.resetPassword(this.resetPasswordForm.value);
  }

}
