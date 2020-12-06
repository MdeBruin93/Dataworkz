import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { 
  CreateComponent,
  EditComponent,
  OverviewComponent,
  DetailComponent
} from './pages';

const routes: Routes = [
  {
    path: '',
    component: OverviewComponent
  },
  {
    path: 'create',
    component: CreateComponent
  },
  {
    path: 'edit/:eventId',
    component: EditComponent
  },
  {
    path:':id',
    component: DetailComponent
  }  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventsRoutingModule { }
