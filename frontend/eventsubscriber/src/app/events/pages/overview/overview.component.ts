import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EventsService, UserService } from '../../services';
import { IEventResponse, IEvent } from '../../models/event.model';
import { Store, Select } from '@ngxs/store';
import { AuthState, CategoriesState, LoadFilterCategories, LoadCategories } from '@core/store';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { WishlistComponent } from 'src/app/wishlists';
import { WishlistService } from '../../../wishlists/services';
import { Category } from '@core/models';
import { FormControl } from '@angular/forms';
import { TagsService } from '../../services/tags.service';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  @Select(AuthState.isLoggedIn)
  public isLoggedIn$: Observable<boolean>;

  @Select(CategoriesState.filterCategories)
  public filterCategories$: Observable<Category[]>;

  public events: any;
  public filteredEvents: any;
  public clickedEventId: string = '';
  public eventsByUser: any;
  public showSubscribedToEvents: boolean = false;

  public categoriesFormControl = new FormControl([]);
  public selectedTags: any[] = [];
  public tags: any[] = [];

  constructor(
    private eventsService: EventsService,
    private wishlistService: WishlistService,
    private userService: UserService,
    private router: Router,
    private store: Store,
    public dialog: MatDialog,
    private tagsService: TagsService
  ) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadCategories());
    this.store.dispatch(new LoadFilterCategories());
    this.tagsService.getAll().subscribe((tags) => {
      this.tags = tags;
    });
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

      this.wishlistService.update(wishlist.id, object).subscribe({
        next: _response => {},
        error: _error => {}
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
    this.filterEvents();
  }

  selectTag(tag: any) {
    this.selectedTags = tag.value;
    this.filterEvents();
  }

  filterEvents() {
    if (this.categoriesFormControl.value.length == 0 && this.selectedTags.length == 0) {
      this.filteredEvents = this.events;
      return;
    }

    let tempFilterEvents = this.events;

    if (this.categoriesFormControl.value.length > 0) {
      tempFilterEvents = tempFilterEvents.filter((event: any) => {
        return this.categoriesFormControl.value.includes(event.category.id)
      });
    }

    if (this.selectedTags.length > 0) {
      tempFilterEvents = tempFilterEvents.filter((e:IEventResponse) => {
        let containTag = false;
        for (var eventTag of e.tags) {
          const index = this.selectedTags.findIndex((st:any) => {
            return st == eventTag.id;
          });
          if (index != -1) {
            containTag = true;
          }
        }
        return containTag;
      });
    }
    this.filteredEvents = tempFilterEvents;
  }
}
