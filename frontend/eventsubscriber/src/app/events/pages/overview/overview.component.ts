import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { EventsService, UserService } from '../../services';
import { IEventResponse } from '../../models/event.model';
import { Store, Select } from '@ngxs/store';
import { AuthState } from '@core/store';
import { Observable, of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { WishlistComponent } from 'src/app/wishlists';
import { WishlistService } from '../../../wishlists/services';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  @Select(AuthState.isLoggedIn)
  public isLoggedIn$: Observable<boolean>;

  public events: any;
  public clickedEventId: string = '';
  public eventsByUser: any;
  public showSubscribedToEvents: boolean = false;

  constructor(
    private eventsService: EventsService,
    private wishlistService: WishlistService,
    private userService: UserService,
    private router: Router,
    private store: Store,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.eventsService.getAll().subscribe({
      next: _response => {
        this.events = _response;
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
    Object.keys(object).forEach(key => console.log(object[key]));
    formData.append('test','test')
    console.log(formData);
    console.log(formData.getAll('name'));
    return formData;
  }

  openWishlistPicker(eventId: number): void {
    const dialogRef = this.dialog.open(WishlistComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(wishlist => {
      console.log('The dialog was closed');
      console.log(`Dialog result: ${wishlist}`);
      let currentEventIds = wishlist.events.map((event:any) => { return event.id });
      currentEventIds.push(eventId);
      currentEventIds = [...new Set(currentEventIds)];

      const object = {
        name: wishlist.name,
        eventIds: currentEventIds
      }

      const fromData = this.getFormData(object);
      console.log(currentEventIds);

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
}
