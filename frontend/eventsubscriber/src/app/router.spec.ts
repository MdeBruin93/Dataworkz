import { Location } from "@angular/common";
import { TestBed, fakeAsync, tick, flush } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";

import { routes } from "./app-routing.module";
import { ForgotPasswordComponent, LoginComponent, RegisterComponent, ResetPasswordComponent } from "./auth";
import { ActivateAccountComponent } from "@auth/pages/activate-account/activate-account.component";
import { AppComponent } from "./app.component";

describe("Router: App", () => {
  let location: Location;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes(routes)],
      declarations: [AppComponent]
    });

    router = TestBed.get(Router);
    location = TestBed.get(Location);
    router.initialNavigation();
  });

  it('navigate to "" redirects you to /home', fakeAsync(() => {
    router.navigate(["login"]).then(() => {
        console.log(location.path());
      expect(location.path()).toBe("/login");
      flush();
    });
  }));

  it('navigate to "search" takes you to /search', fakeAsync(() => {
    router.navigate(["/register"]).then(() => {
    console.log(location.path());
      expect(location.path()).toBe("/register");
    });
    flush();
  }));
});