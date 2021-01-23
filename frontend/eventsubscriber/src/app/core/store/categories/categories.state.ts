import { Injectable } from '@angular/core';
import { State, Selector, StateContext, Action } from '@ngxs/store';

import { CategoriesStateModel } from './categories.state-model';
import { LoadCategories, LoadCategory, SaveCategory, DeleteCategory, LoadFilterCategories } from './categories.actions';
import { Category } from '@core/models';
import { IEvent } from '@events/models';

import { CategoriesService } from '@categories/services';
import { EventsService } from '@events/services';



@State<CategoriesStateModel>({
  name: 'categories',
  defaults: {
    categories: [],
    filterCategories: [],
    category: null
  },
})
@Injectable()
export class CategoriesState {
  public constructor(
    private categoriesService: CategoriesService,
    private eventsService: EventsService
  ) { }

  @Selector()
  static categories(state: CategoriesStateModel): Category[] {
    return state.categories;
  }

  @Selector()
  static filterCategories(state: CategoriesStateModel): Category[] {
    return state.filterCategories;
  }

  @Selector()
  static category(state: CategoriesStateModel): Category | null {
    return state.category;
  }

  @Action(LoadCategories)
  public async loadCategories(ctx: StateContext<CategoriesStateModel>, { }: LoadCategories): Promise<void> {
    await this.getCategories(ctx);
  }

  @Action(LoadCategory)
  public async loadCategory(ctx: StateContext<CategoriesStateModel>, { id }: LoadCategory): Promise<void> {
    const category = await this.categoriesService.findById(id).toPromise();
    ctx.patchState({ category: category });
  }

  @Action(SaveCategory)
  public async SaveCategory(ctx: StateContext<CategoriesStateModel>, { category }: SaveCategory): Promise<void> {
    const savedCategory = await this.categoriesService.save(category).toPromise();
    ctx.patchState({ category: savedCategory });
  }

  @Action(DeleteCategory)
  public async DeleteCategory(ctx: StateContext<CategoriesStateModel>, { id }: DeleteCategory): Promise<void> {
    await this.categoriesService.delete(id).toPromise();
    await this.getCategories(ctx);
  }

  @Action(LoadFilterCategories)
  public async loadFilterCategories(ctx: StateContext<CategoriesStateModel>, {}: LoadFilterCategories): Promise<void> {
    let categories = await this.categoriesService.getAll().toPromise();
    const events = await this.eventsService.getAll().toPromise();
    const usedCategories = events
      .filter((e: IEvent) => {
        return new Date(e.date).getTime() > new Date().getTime();
      })
      .map((e: IEvent) => {
        return e.category.id;
      });
    categories = categories.filter((c) => {
      return !c.deleted || usedCategories.includes(c.id);
    });
    ctx.patchState({ filterCategories: categories });
  }

  private async getCategories(ctx: StateContext<CategoriesStateModel>) {
    let categories = await this.categoriesService.getAll().toPromise();
    categories = categories.filter((c) => !c.deleted);
    ctx.patchState({ categories: categories });
  }
}
