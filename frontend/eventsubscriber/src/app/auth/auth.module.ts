import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import {
  LoginComponent,
  RegisterComponent,
  ForgotPasswordComponent
} from './pages';

import {
  AuthService
} from './services';



@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  exports: [
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent
  ],
  providers: [
    AuthService
  ]
})
export class AuthModule { }
