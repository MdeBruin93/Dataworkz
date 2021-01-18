export class Login {
  static readonly type = '[Auth] Login';

  public constructor(public email: string, public password: string) {}
}


export class Logout {
  static readonly type = '[Auth] Logout';

  public constructor() {}
}

export class SetCurrentUser {
  static readonly type = '[Auth] Set current user';

  public constructor(public data: any) {}
}
