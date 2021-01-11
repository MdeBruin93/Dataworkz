import { Component, OnInit } from '@angular/core';
import { Store, Select } from '@ngxs/store';
import { CategoriesState, LoadCategories } from '@core/store';
import { Category } from '@core/models';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {
  @Select(CategoriesState.categories)
  public categories$: Observable<Category | any>;

  public categoryForm = Category.getFormGroup();

  constructor(
    private store: Store
  ) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadCategories());
  }

  deletecategory(id: string) {
    // dispatch delete category
  }

  edit(index: number) {
    // dispatch save
  }

  onSubmitCategoryForm(category: Category) {
    // dispatch save
  }
}
