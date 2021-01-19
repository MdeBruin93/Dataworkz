import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { QaService } from '../../services/qa.service';

import { QaComponent } from './qa.component';

describe('QaComponent', () => {
  let component: QaComponent;
  let qaService: QaService;
  let snackBar: MatSnackBar;

  beforeEach(() => {
    component = new QaComponent(
      qaService,
      snackBar
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
