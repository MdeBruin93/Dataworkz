import { Location } from "@angular/common";
import { TestBed, fakeAsync, tick, flush } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";

import { routes } from "./users-routing.module";

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

  it('navigate to "settings" redirects you to /settings', fakeAsync(() => {
    router.navigate(["settings"]).then(() => {
      expect(location.path()).toBe("/settings");
    });
    flush();
  }));
});