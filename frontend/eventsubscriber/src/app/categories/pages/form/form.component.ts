import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Category } from '@core/models';

import { Store, Select} from '@ngxs/store';
import { CategoriesState, LoadCategory, SaveCategory } from '@core/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {
  @Select(CategoriesState.category)
  public category$: Observable<Category>;

  public categoryForm = Category.getFormGroup();

  public currentCategoryId: number;

  constructor(
    private store: Store,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.currentCategoryId = this.route.snapshot.params.id || null;
    if (this.currentCategoryId) {
        this.category$.subscribe(response => {
          if (response) {
              this.categoryForm.patchValue(response);
          }
        });
        this.store.dispatch(new LoadCategory(this.currentCategoryId));
    }
  }

  saveCategory() {
    this.store.dispatch(new SaveCategory(this.categoryForm.value));
    this.router.navigate(["/categories"]);
  }
}
