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
  wishlists: any = [];
  toggle: any = [];
  panelOpenState = false;

  constructor(
    private wishlistService: WishlistService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.wishlistService.findByUser().subscribe({
      next: response => {
        console.log(response);
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

  onSubmit() {
    var formData: any = new FormData();
    const name = this.wishlistCreateForm.get('name') || {value: null};
    formData.append("name", name.value);

    this.wishlistService.create(formData).subscribe({
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

}
