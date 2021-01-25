import { Location } from "@angular/common";
import { TestBed, fakeAsync, tick, flush } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";

import { routes } from "./auth-routing.module";

describe("Router: Events", () => {
  let location: Location;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes(routes)]
    });

    router = TestBed.get(Router);
    location = TestBed.get(Location);
    router.initialNavigation();
  });

  it('navigate to "login" redirects you to /login', fakeAsync(() => {
    router.navigate(["login"]).then(() => {
      expect(location.path()).toBe("/login");
    });
    flush();
  }));

  it('navigate to "register" redirects you to /register', fakeAsync(() => {
    router.navigate(["register"]).then(() => {
      expect(location.path()).toBe("/register");
    });
    flush();
  }));

  it('navigate to "forgot-password/123" takes you to /forgot-password/123', fakeAsync(() => {
    router.navigate(["/forgot-password/123"]).then(() => {
      expect(location.path()).toBe("/forgot-password/123");
    });
    flush();
  }));

  it('navigate to "activate-account/123" takes you to /activate-account/123', fakeAsync(() => {
    router.navigate(["/activate-account/123"]).then(() => {
      expect(location.path()).toBe("/activate-account/123");
    });
    flush();
  }));
});