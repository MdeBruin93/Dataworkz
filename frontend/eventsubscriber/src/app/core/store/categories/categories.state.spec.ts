import { CategoriesService } from '@categories/services';
import { CategoriesState } from './categories.state';

describe('categoriesState', () => {
  let categoriesService: CategoriesService;
  let categoriesState: CategoriesState;

  beforeEach(() => {
    categoriesState = new CategoriesState(categoriesService);
  });

  it('should create', () => {
    expect(categoriesState).toBeTruthy();
  });
});