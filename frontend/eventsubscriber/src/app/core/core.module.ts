import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../material/material.module';

import { NavComponent } from './components/nav/nav.component';
import { UnauthenticatedLayoutComponent } from './components/unauthenticated-layout/unauthenticated-layout.component';
import { AuthenticatedLayoutComponent } from './components/authenticated-layout/authenticated-layout.component';

import { IsLoggedInDirective, IsAdminDirective } from './directives';
import { NotFoundComponent } from './components/not-found/not-found.component';

@NgModule({
  declarations: [
    NavComponent,
    UnauthenticatedLayoutComponent,
    AuthenticatedLayoutComponent,
    IsLoggedInDirective,
    IsAdminDirective,
    NotFoundComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    BrowserModule,
    RouterModule,
  ],
  exports: [
    NavComponent,
    UnauthenticatedLayoutComponent,
    AuthenticatedLayoutComponent,
    IsLoggedInDirective,
    IsAdminDirective
  ]
})
export class CoreModule { }
