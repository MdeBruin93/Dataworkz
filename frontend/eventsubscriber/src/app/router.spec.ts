import { Location } from "@angular/common";
import { TestBed, fakeAsync, tick, flush } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";

import { routes } from "./app-routing.module";
import { AuthGuard, AuthService, ForgotPasswordComponent, LoginComponent, RegisterComponent, ResetPasswordComponent } from "./auth";
import { ActivateAccountComponent } from "@auth/pages/activate-account/activate-account.component";
import { AppComponent } from "./app.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";

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

  it('navigate to "" redirects you to /login', fakeAsync(() => {
    router.navigate([""]).then(() => {
      expect(location.path()).toBe("/login");
    });
    flush();
  }));

  it('navigate to "login" redirects you to /login', fakeAsync(() => {
    router.navigate(["login"]).then(() => {
      expect(location.path()).toBe("/login");
    });
    flush();
  }));

  it('navigate to "register" takes you to /register', fakeAsync(() => {
    router.navigate(["/register"]).then(() => {
      expect(location.path()).toBe("/register");
    });
    flush();
  }));

  it('navigate to "forgot-password" takes you to /forgot-password', fakeAsync(() => {
    router.navigate(["/forgot-password"]).then(() => {
      expect(location.path()).toBe("/forgot-password");
    });
    flush();
  }));

  it('navigate to "reset-password/:token" takes you to reset-password/:token', fakeAsync(() => {
    router.navigate(["/reset-password/123"]).then(() => {
      expect(location.path()).toBe("/reset-password/123");
    });
    flush();
  }));

  it('navigate to "activate-password/:token" takes you to /activate-password/:token', fakeAsync(() => {
    router.navigate(["/activate-password/123"]).then(() => {
      expect(location.path()).toBe("/activate-password/123");
    });
    flush();
  }));
});