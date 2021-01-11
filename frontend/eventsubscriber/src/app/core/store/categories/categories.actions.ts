export class LoadCategories {
  static readonly type = '[Categories] Load categories';

  public constructor() {}
}


export class LoadCategory {
  static readonly type = '[Categories] Load category';

  public constructor(public id: number) {}
}
