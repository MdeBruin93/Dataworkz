import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './auth';

import {
  AuthenticatedLayoutComponent,
  UnauthenticatedLayoutComponent
} from './core';

import {
  LoginComponent,
  RegisterComponent,
  ForgotPasswordComponent,
  ResetPasswordComponent
} from './auth';

import {
  DashboardComponent
} from './dashboard';
import { WishlistComponent } from './wishlists';
import { ActivateAccountComponent } from '@auth/pages/activate-account/activate-account.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: '',
    component: UnauthenticatedLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegisterComponent
      },
      {
        path: 'forgot-password',
        component: ForgotPasswordComponent
      },
      {
        path: 'reset-password/:token',
        component: ResetPasswordComponent
      },
      {
        path: 'activate-password/:token',
        component: ActivateAccountComponent
      },
      {
        path: 'events',
        loadChildren: () => import('./events/events.module').then((m) => m.EventsModule),
      }
    ],

  },
  {
    path: '',
    component: AuthenticatedLayoutComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent
      },
      {
        path: 'wishlists',
        loadChildren: () => import('./wishlists/wishlists.module').then((m) => m.WishlistsModule),
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload', })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
