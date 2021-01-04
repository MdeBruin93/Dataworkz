import { Directive, OnInit, TemplateRef, ViewContainerRef, Input } from '@angular/core';

@Directive({ selector: '[appIsLoggedIn]' })
export class IsLoggedInDirective implements OnInit {
  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) {}

  isLoggedIn: boolean = false;

  @Input()
  set appAuth(isLoggedIn: boolean) {
    this.isLoggedIn = isLoggedIn;
  }

  ngOnInit(): void {
    let hasAccess = false;

    if (this.isLoggedIn === true) {
      hasAccess = true;
    }

    if (hasAccess) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
