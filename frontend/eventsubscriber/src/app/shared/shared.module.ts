import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IsAllowedForOrganizerDirective } from './directives/isAllowedForOrganizer.directive';
import { IsNotAllowedForOrganizerDirective } from './directives/isNotAllowedForOrganizer.directive';

@NgModule({
  declarations: [
    IsAllowedForOrganizerDirective,
    IsNotAllowedForOrganizerDirective
  ],
  imports: [
    CommonModule
  ],
  exports: [
    IsAllowedForOrganizerDirective,
    IsNotAllowedForOrganizerDirective
  ]
})
export class SharedModule { }
