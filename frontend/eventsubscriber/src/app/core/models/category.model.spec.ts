import { Category } from './category.model';

describe('authAction', () => {
  let categoryModel: Category;

  beforeEach(() => {
    categoryModel = new Category('string', 'string', 1);
  });

  it('should create', () => {
    expect(categoryModel).toBeTruthy();
  });
});