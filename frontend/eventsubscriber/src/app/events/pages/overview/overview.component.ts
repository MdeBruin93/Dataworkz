import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { EventsService, UserService } from '../../services';
import { IEventResponse, IEvent } from '../../models/event.model';
import { Store, Select } from '@ngxs/store';
import { AuthState, CategoriesState, LoadCategories } from '@core/store';
import { Observable, of } from 'rxjs';
import { switchMap, filter } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { WishlistComponent } from 'src/app/wishlists';
import { WishlistService } from '../../../wishlists/services';
import { Category } from '@core/models';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  @Select(AuthState.isLoggedIn)
  public isLoggedIn$: Observable<boolean>;

  @Select(CategoriesState.categories)
  public categories$: Observable<Category[]>;

  public events: any;
  public filteredEvents: any;
  public clickedEventId: string = '';
  public eventsByUser: any;
  public showSubscribedToEvents: boolean = false;

  public categoriesFormControl = new FormControl([]);

  constructor(
    private eventsService: EventsService,
    private wishlistService: WishlistService,
    private userService: UserService,
    private router: Router,
    private store: Store,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadCategories());
    this.eventsService.getAll().subscribe({
      next: _response => {
        console.log(_response);
        this.events = _response || [];
        this.filteredEvents = this.events;
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });

    this.isLoggedIn$.pipe(
      switchMap((val: boolean) => {
        if (val) {
          return this.userService.subscribedToEvents();
        }
        throw new Error('Is not authenticated');
      })
    ).subscribe({
      next: _response => {
        this.eventsByUser = _response;
      },
      error: error => {}
    });
  }

  navigateToDetail(event: IEventResponse) {
    this.router.navigate(['/events', event.id]);
  }

  subscribeToEvent(id: number, event: any) {
    event.stopPropagation();
    event.preventDefault();
    this.eventsService.subscribe(id).subscribe({
      next: _response => {
        console.log(_response);
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

  getFormData(object: any) {
    const formData = new FormData();
    Object.keys(object).forEach(key => formData.append(key, object[key]));
    return formData;
  }

  openWishlistPicker(eventId: number): void {
    const dialogRef = this.dialog.open(WishlistComponent, {
      width: '1300px'
    });

    dialogRef.afterClosed().subscribe(wishlist => {
      let currentEventIds = wishlist.events.map((event:any) => { return event.id });
      currentEventIds.push(eventId);
      currentEventIds = [...new Set(currentEventIds)];

      const object = {
        name: wishlist.name,
        eventIds: currentEventIds
      }

      const fromData = this.getFormData(object);

      this.wishlistService.update(wishlist.id, fromData).subscribe({
        next: _response => {
          console.log(_response);
        },
        error: error => {
          console.error('There was an error!', error);
        }
      });
    });
  }

  deleteEvent(id: number) {
    this.eventsService.delete(id).subscribe({
      next: (_response: any) => {
        this.ngOnInit();
        console.log(_response);
      },
      error: (error: any) => {
        console.error('There was an error!', error);
      }
    });
  }

  sanitize(url: string) {
    return this.eventsService.sanitize(url);
  }

  selectCategory() {
    this.filteredEvents = this.events.filter((event: any) => this.categoriesFormControl.value.includes(event.category.id));
  }
}
