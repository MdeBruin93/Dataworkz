import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { UsersRoutingModule } from './users-routing.module';

import { UserSettingsComponent } from './pages/user-settings/user-settings.component';
import { OverviewComponent } from './pages/overview/overview.component';





@NgModule({
  declarations: [UserSettingsComponent, OverviewComponent],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule,
    UsersRoutingModule
  ]
})
export class UsersModule { }
