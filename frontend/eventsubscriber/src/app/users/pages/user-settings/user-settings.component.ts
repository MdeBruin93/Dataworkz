import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { Store, Select } from '@ngxs/store';
import { EditAccount, AuthState } from '@core/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.scss']
})
export class UserSettingsComponent implements OnInit {
  @Select(AuthState.currentUser)
  public currentUser$: Observable<any>;

  userSettingsForm = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required])
  });

  constructor(
    private store: Store,
    private route: Router
  ) { }

  ngOnInit(): void {
    this.currentUser$.subscribe((user) => {
      this.userSettingsForm.patchValue(user);
    });
  }

  onSubmit() {
    this.store.dispatch(new EditAccount(this.userSettingsForm.value));
    this.route.navigate(['/events']);
  }
}
