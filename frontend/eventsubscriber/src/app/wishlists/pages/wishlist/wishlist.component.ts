import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Wishlist } from '../../models';
import { WishlistService } from '../../services';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {
  wishlistCreateForm: FormGroup = Wishlist.getFormGroup();
  wishlistEditForm: FormGroup = Wishlist.getFormGroup();
  wishlists: any = [];
  toggle: any = [];
  edit: any = [];
  panelOpenState = false;

  constructor(
    private wishlistService: WishlistService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.wishlistService.findByUser().subscribe({
      next: response => {
        console.log('Hello world!');
        this.wishlists = response;
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

  deleteWishlist(id: number) {
    this.wishlistService.delete(id).subscribe({
      next: _response => {
        this.snackBar.open('Wishlist successfully deleted');
        this.ngOnInit();
      },
      error: error => {
        this.snackBar.open('Failed to delete your wishlist');
        console.error('There was an error!', error);
      }
    });
  }

  deleteEventFromWishlist(wishlist: any, eventId: number) {
    let currentEventIds = wishlist.events.map((event:any) => { return event.id });

    const index = currentEventIds.indexOf(eventId);
    if (index > -1) {
      currentEventIds.splice(index, 1);
    }

    const object = {
      name: wishlist.name,
      eventIds: currentEventIds
    }

    this.wishlistService.update(wishlist.id, object).subscribe({
      next: _response => {
        this.ngOnInit();
        console.log(_response);
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

  onSubmit() {
    const name = this.wishlistCreateForm.get('name') || {value: null};

    const object = {
      name: name.value
    }

    this.wishlistService.create(object).subscribe({
      next: _response => {
        this.snackBar.open('Event successfully created');
        this.ngOnInit();
      },
      error: error => {
        this.wishlistCreateForm.reset();
        this.snackBar.open('Failed to create your event');
        console.error('There was an error!', error);
      }
    });
  }

  onSubmitEditForm(wishlist: any) {
    const name = this.wishlistEditForm.get('name') || {value: null};
    let currentEventIds = wishlist.events.map((event:any) => { return event.id });
    const object = {
      name: name.value,
      eventIds: currentEventIds
    }

    this.wishlistService.update(wishlist.id, object).subscribe({
      next: _response => {
        this.snackBar.open('Event successfully updated');
        this.ngOnInit();
        this.edit = [];
      },
      error: error => {
        this.wishlistCreateForm.reset();
        this.snackBar.open('Failed to update your event');
        console.error('There was an error!', error);
      }
    });
  }

}
