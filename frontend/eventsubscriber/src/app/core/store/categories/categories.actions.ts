import { Category } from '@core/models';

export class LoadCategories {
  static readonly type = '[Categories] Load categories';

  public constructor() {}
}

export class LoadFilterCategories {
  static readonly type = '[Categories] Load filter categories';

  public constructor() {}
}

export class LoadCategory {
  static readonly type = '[Categories] Load category';

  public constructor(public id: number) {}
}

export class SaveCategory {
  static readonly type = '[Categories] Save category';

  public constructor(public category: Category) {}
}

export class DeleteCategory {
  static readonly type = '[Categories] Delete category';

  public constructor(public id: number) {}
}
