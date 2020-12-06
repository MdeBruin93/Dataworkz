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
  ForgotPasswordComponent
} from './auth';

import {
  DashboardComponent
} from './dashboard'; 
import { CreateComponent, EditComponent } from './events';


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
      }
    ]
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
        path: 'events',
        loadChildren: () => import('./events/events.module').then((m) => m.EventsModule),
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
