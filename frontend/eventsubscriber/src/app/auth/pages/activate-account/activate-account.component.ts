import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '@auth/services';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrls: ['./activate-account.component.scss']
})
export class ActivateAccountComponent implements OnInit {
  token: string = '';
  activationSuccessfull: boolean = false;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
  ) { }

  ngOnInit(): void {
    this.token = this.route.snapshot.params.token;

    const activateAccountObject = {
      token: this.token
    }

    this.authService.activateAccount(activateAccountObject).subscribe(
      (_response) => {
        this.activationSuccessfull = true;
      }
    );
  }
}
