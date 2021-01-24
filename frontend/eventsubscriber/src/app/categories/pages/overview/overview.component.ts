import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Store, Select } from '@ngxs/store';
import { CategoriesState, LoadCategories, DeleteCategory } from '@core/store';
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
    private store: Store,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.store.dispatch(new LoadCategories());
  }

  deletecategory(id: number) {
    this.store.dispatch(new DeleteCategory(id));
  }

  edit(id: number) {
    this.router.navigate(["categories/create"]);
  }
}
