import { Injectable } from '@angular/core';
import { State, Selector, StateContext, Action } from '@ngxs/store';

import { CategoriesStateModel } from './categories.state-model';
import { LoadCategories, LoadCategory } from './categories.actions';
import { Category } from '@core/models';

import { CategoriesService } from '@categories/services';



@State<CategoriesStateModel>({
  name: 'categories',
  defaults: {
    categories: [],
    category: null
  },
})
@Injectable()
export class CategoriesState {
  public constructor(
    private categoriesService: CategoriesService
  ) { }

  @Selector()
  static categories(state: CategoriesStateModel): Category[] {
    return state.categories;
  }

  @Selector()
  static category(state: CategoriesStateModel): Category | null {
    return state.category;
  }

  @Action(LoadCategories)
  public async loadCategories(ctx: StateContext<CategoriesStateModel>, { }: LoadCategories): Promise<void> {
    const categories = await this.categoriesService.getAll().toPromise();
    console.log(categories);
    ctx.patchState({ categories: categories });
  }

  @Action(LoadCategory)
  public async loadCategory(ctx: StateContext<CategoriesStateModel>, { id }: LoadCategory): Promise<void> {
    const category = await this.categoriesService.findById(id).toPromise();
    ctx.patchState({ category: category });
  }
}
