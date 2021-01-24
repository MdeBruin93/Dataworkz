import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserSettingsComponent, OverviewComponent } from './pages';

export const routes: Routes = [
  {
    path: '',
    component: OverviewComponent
  },
  {
    path: 'settings',
    component: UserSettingsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
