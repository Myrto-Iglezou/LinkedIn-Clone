import { Message } from './../../model/message';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { AuthenticationService } from '../../authentication.service';
import { NgForm } from '@angular/forms';
import { User } from '../../model/user';
import { UserDetails } from '../../model/user-details';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  user: User;
  model: any = {};
  loading = false;
  returnUrl: string;
  loginerror: any;
  loginmsg: string;
  dangerBox = false;
  submitattempt = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    document.body.className = 'selector';
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      if (userDetails) {
        this.router.navigate([this.makeRedirectUrl(userDetails)]);
      }
    });
  }

  makeRedirectUrl(userDetails: UserDetails): string {
    let redirectUrl: string = null;
    if (this.hasRole('ADMIN', userDetails)) redirectUrl = '/admin';
    else if (this.hasRole('PROFESSIONAL', userDetails)) redirectUrl = '/feed';    
    else redirectUrl = '/login';
    return redirectUrl;
  }

  hasRole(rolename: string, userDetails: UserDetails): boolean {
    let flag = false;
    if (userDetails) {
      userDetails.roles.forEach((role) => {
        if (role === rolename) flag = true;
      });
    }
    return flag;
  }

  login(loginform) {
    if (loginform.form.valid) {
      this.loading = true;
      this.authenticationService
        .login(this.model.username, this.model.password)
        .subscribe(
          (response) => {
            const userDetails = new UserDetails();
            this.user = response.body;
            userDetails.id = this.user.id;
            userDetails.token = response.headers.get('Authorization');
            this.user.roles.forEach((role) => {
              if(role.name === 'ADMIN') this.returnUrl = '/admin';
              else if (role.name === 'PROFESSIONAL') this.returnUrl = '/feed';             
              
              userDetails.roles.push(role.name);
            });
            
            this.authenticationService.setLoggedInUser(userDetails);
            
            this.route.queryParams.subscribe((params) => {
              if (params && params.returnUrl) {
                this.router.navigate([params.returnUrl]).then(() => {
                  location.reload();
                });
              } else this.router.navigate([this.makeRedirectUrl(userDetails)]); 
            });
          },
          (error) => {
            this.loading = false;
            this.loginerror = error;
            if (error.error.message === 'Bad credentials') {
              this.loginmsg = ': Invalid username or password';
            }
            this.dangerBox = true;
            this.submitattempt = true;
          }
        );
    } else {
      this.submitattempt = true;
      this.dangerBox = false;
    }
  }
}
