import { Category } from '@core/models';

export interface CategoriesStateModel {
  categories: Category[],
  category: Category | null
}
