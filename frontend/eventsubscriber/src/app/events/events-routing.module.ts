import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { 
  FormComponent,
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
    component: FormComponent
  },
  {
    path: 'edit/:eventId',
    component: FormComponent
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
