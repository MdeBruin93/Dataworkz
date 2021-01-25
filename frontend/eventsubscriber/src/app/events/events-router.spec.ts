import { Location } from "@angular/common";
import { TestBed, fakeAsync, tick, flush } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";

import { routes } from "./events-routing.module";

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

  it('navigate to "" redirects you to /', fakeAsync(() => {
    router.navigate([""]).then(() => {
      expect(location.path()).toBe("/");
    });
    flush();
  }));

  it('navigate to "create" redirects you to /create', fakeAsync(() => {
    router.navigate(["create"]).then(() => {
      expect(location.path()).toBe("/create");
    });
    flush();
  }));

  it('navigate to "edit/123" takes you to /edit/123', fakeAsync(() => {
    router.navigate(["/edit/123"]).then(() => {
      expect(location.path()).toBe("/edit/123");
    });
    flush();
  }));

  it('navigate to "123" takes you to /123', fakeAsync(() => {
    router.navigate(["/123"]).then(() => {
      expect(location.path()).toBe("/123");
    });
    flush();
  }));
});