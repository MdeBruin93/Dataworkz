import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WishlistsRoutingModule } from './wishlists-routing.module';
import { WishlistComponent } from './pages/wishlist/wishlist.component';
import { MaterialModule } from '../material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [WishlistComponent],
  imports: [
    CommonModule,
    WishlistsRoutingModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [
    WishlistComponent
  ]
})
export class WishlistsModule { }
