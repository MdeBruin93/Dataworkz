import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs';
import { Select } from '@ngxs/store';
import { AuthState } from '@core/store';

@Component({
  selector: 'app-unauthenticated-layout',
  templateUrl: './unauthenticated-layout.component.html',
  styleUrls: ['./unauthenticated-layout.component.scss']
})
export class UnauthenticatedLayoutComponent implements OnInit {
  @Select(AuthState.isLoggedIn)
  public isLoggedIn$: Observable<boolean>;

  constructor() { }

  ngOnInit(): void {
  }

}
