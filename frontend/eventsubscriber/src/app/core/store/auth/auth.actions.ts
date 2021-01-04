export class Login {
  static readonly type = '[Auth] Login';

  public constructor(public email: string, public password: string) {}
}


export class Logout {
  static readonly type = '[Auth] Logout';

  public constructor() {}
}