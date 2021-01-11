import { Directive, OnInit, TemplateRef, ViewContainerRef, Input } from '@angular/core';
import { AuthService } from '@auth/services';

@Directive({ selector: '[appIsNotAllowedForOrganizer]' })
export class IsNotAllowedForOrganizerDirective implements OnInit {
  constructor(
    private templateRef: TemplateRef<any>,
    private authService: AuthService,
    private viewContainer: ViewContainerRef
  ) {}

  ownerId: number | null;

  @Input()
  set appIsNotAllowedForOrganizer(ownerId: number) {
    this.ownerId = ownerId;
  }

  ngOnInit(): void {
    let hasAccess = false;

    if (this.authService.isAuthenticated()) {
      hasAccess = true;
    }

    if (this.ownerId) {
      const user = this.authService.getCurrentUser();
      hasAccess = (user != null && user.id != this.ownerId);
    }

    if (hasAccess) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
