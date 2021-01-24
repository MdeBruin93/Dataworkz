import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { QaService } from '../../services/qa.service';
import createSpyObj = jasmine.createSpyObj;
import SpyObj = jasmine.SpyObj;

import { QaComponent } from './qa.component';
import { ActivatedRoute } from '@angular/router';
import { defer } from 'rxjs';
import { FormGroup } from '@angular/forms';

describe('QaComponent', () => {
  let component: QaComponent;
  let qaService: QaService;
  let snackBar: SpyObj<MatSnackBar>
  let qaServiceMock: SpyObj<QaService>;
  let routerMock: SpyObj<ActivatedRoute>;
  let qaCreateform: SpyObj<FormGroup>

  beforeEach(() => {
    routerMock = createSpyObj('ActivatedRoute', ['toStrings', 'navigate'], {snapshot: {params: {id: undefined}}});
    snackBar = createSpyObj('MatSnackBar', ['open']);
    qaCreateform = createSpyObj('FormGroup', ['reset']);
    qaServiceMock = createSpyObj('QaService', ['create', 'delete', 'update', 'createAnswer', 'deleteAnswer', 'updateAnswer']);
    component = new QaComponent(
      qaServiceMock,
      snackBar
    );
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('deleteQuestion', () => {
    it('should do next when success', () => {
      qaServiceMock.delete.and.returnValue(defer(() => Promise.resolve()));

      component.deleteQuestion(1);
      expect(qaServiceMock.delete).toHaveBeenCalledWith(1);
    });

    it('should do error when failed', () => {
      qaServiceMock.delete.and.returnValue(defer(() => Promise.reject()));

      component.deleteQuestion(1);
      expect(qaServiceMock.delete).toHaveBeenCalledWith(1);
    });
  });

  describe('deleteAnswer', () => {
    it('should do next when success', () => {
      qaServiceMock.deleteAnswer.and.returnValue(defer(() => Promise.resolve()));

      component.deleteAnswer(1);
      expect(qaServiceMock.deleteAnswer).toHaveBeenCalledWith(1);
    });

    it('should do error when failed', () => {
      qaServiceMock.deleteAnswer.and.returnValue(defer(() => Promise.reject()));

      component.deleteAnswer(1);
      expect(qaServiceMock.deleteAnswer).toHaveBeenCalledWith(1);
    });
  });

  describe('onSubmit', () => {
    it('should do next when success', () => {
      qaServiceMock.create.and.returnValue(defer(() => Promise.resolve({id: 1})));
      component.onSubmit();

      expect(qaServiceMock.create).toHaveBeenCalled();
      //expect(qaCreateform.reset).toHaveBeenCalled();
    });

    it('should do error when failed', () => {
      qaServiceMock.create.and.returnValue(defer(() => Promise.reject()));

      component.onSubmit();
      expect(qaServiceMock.create).toHaveBeenCalled();
    });
  });
});
