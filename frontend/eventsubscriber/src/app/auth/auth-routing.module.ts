import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';

import { 
  RegisterComponent,
  LoginComponent
} from './pages';
import { ActivateAccountComponent } from './pages/activate-account/activate-account.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'forgot-password/:token',
    component: ForgotPasswordComponent
  },
  {
    path: 'activate-account/:token',
    component: ActivateAccountComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
