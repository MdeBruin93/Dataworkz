import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OverviewComponent } from './pages/overview/overview.component';
import { FormComponent } from './pages/form/form.component';
import { DetailComponent } from './pages/detail/detail.component';

import { CategoriesService } from './services';
import { CategoriesRoutingModule } from './categories-routing.module';
import { MaterialModule } from '../material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    OverviewComponent,
    FormComponent,
    DetailComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    CategoriesRoutingModule,
    MaterialModule,
    ReactiveFormsModule

  ],
  providers: [
    CategoriesService
  ]
})
export class CategoriesModule { }
