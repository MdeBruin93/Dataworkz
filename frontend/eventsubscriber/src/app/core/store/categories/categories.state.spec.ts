import { CategoriesService } from '@categories/services';
import { EventsService } from 'src/app/events';
import { CategoriesState } from './categories.state';

describe('categoriesState', () => {
  let categoriesService: CategoriesService;
  let categoriesState: CategoriesState;
  let eventsService: EventsService;

  beforeEach(() => {
    categoriesState = new CategoriesState(categoriesService,eventsService);
  });

  it('should create', () => {
    expect(categoriesState).toBeTruthy();
  });
});