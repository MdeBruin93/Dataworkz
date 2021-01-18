export class LoadUsers {
  static readonly type = '[Users] Load Users';

  public constructor() {}
}


export class LoadBlockedUsers {
  static readonly type = '[Users] Load Blocked Users';

  public constructor() {}
}

export class BlockUser {
  static readonly type = '[Users] Block User';

  public constructor(public id: number) {}
}

export class DeBlockUser {
  static readonly type = '[Users] DeBlock User';

  public constructor(public id: number) {}
}

export class EditAccount {
  static readonly type = '[Users] Edit Account';

  public constructor(public data: any) {}
}
