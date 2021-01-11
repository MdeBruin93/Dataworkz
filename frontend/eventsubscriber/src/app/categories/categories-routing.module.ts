import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OverviewComponent, FormComponent } from './pages';

const routes: Routes = [
  {
    path: '',
    component: OverviewComponent
  },
  {
    path: '/create',
    component: FormComponent
  },
  {
    path: '/{id}/edit',
    component: FormComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoriesRoutingModule { }
