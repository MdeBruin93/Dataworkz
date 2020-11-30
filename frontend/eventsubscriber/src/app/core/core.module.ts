import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material/material.module';
import { NavComponent } from './components/nav/nav.component';
import { UnauthenticatedLayoutComponent } from './components/unauthenticated-layout/unauthenticated-layout.component';
import { AuthenticatedLayoutComponent } from './components/authenticated-layout/authenticated-layout.component';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    NavComponent,
    UnauthenticatedLayoutComponent,
    AuthenticatedLayoutComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    BrowserModule,
    RouterModule
  ],
  exports: [
    NavComponent,
    UnauthenticatedLayoutComponent,
    AuthenticatedLayoutComponent
  ]
})
export class CoreModule { }
