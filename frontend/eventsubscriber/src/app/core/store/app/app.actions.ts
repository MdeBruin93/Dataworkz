export class InitializeApp {
  static readonly type = '[App] Initialize App';
}

export class ToggleSidebar {
  static readonly type = '[App] Toggle Sidebar';
}

export class SetTitle {
  static readonly type = '[App] Set Title';

  public constructor(public title: string) {}
}
