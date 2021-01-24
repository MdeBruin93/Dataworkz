import { Category } from '@core/models';

export interface CategoriesStateModel {
  categories: Category[],
  filterCategories: Category[],
  category: Category | null
}
