import { Directive, OnInit, TemplateRef, ViewContainerRef, Input } from '@angular/core';

@Directive({ selector: '[appIsAdmin]' })
export class IsAdminDirective implements OnInit {
  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) {}

  isAdmin: boolean = false;

  @Input()
  set appIsAdmin(role: string | undefined) {
    console.log(role);
    this.isAdmin = role === "ROLE_ADMIN";
  }

  ngOnInit(): void {
    let hasAccess = false;

    if (this.isAdmin === true) {
      hasAccess = true;
    }

    if (hasAccess) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
