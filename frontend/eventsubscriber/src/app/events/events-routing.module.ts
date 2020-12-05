import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { 
  CreateComponent,
  EditComponent 
} from './pages';

const routes: Routes = [
  {
    path: 'create',
    component: CreateComponent
  },
  {
    path: 'edit/:eventId',
    component: EditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventsRoutingModule { }
