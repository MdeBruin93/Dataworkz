import { DeleteCategory, LoadCategory, SaveCategory } from './categories.actions';

describe('authAction', () => {
  let loadCategory: LoadCategory;
  let saveCategory: SaveCategory;
  let deleteCategory: DeleteCategory;

  beforeEach(() => {
    loadCategory = new LoadCategory(1);
    saveCategory = new SaveCategory({id: 1, "name": "string", "color": "string", endDate: undefined, deleted: false});
    deleteCategory = new DeleteCategory(1);
  });

  it('should create', () => {
    expect(loadCategory).toBeTruthy();
    expect(saveCategory).toBeTruthy();
    expect(deleteCategory).toBeTruthy();
  });
});