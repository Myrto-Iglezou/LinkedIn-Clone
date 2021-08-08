import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {FormControl, Validators,FormGroup} from '@angular/forms';
import { AuthenticationService } from '../authentication.service';
import {NgForm} from '@angular/forms';
import {User} from '../model/user';
import {UserDetails} from '../model/user-details';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User;
  model: any = {};
  loading = false;
  returnUrl: string;
  loginerror: any ;
  loginmsg: string;
  dangerBox = false;
  submitattempt = false;
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService
) { }

ngOnInit(): void {
    document.body.className = "selector";
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
        // this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
    //   if (userDetails) {
    //     this.router.navigate([this.makeRedirectUrl(userDetails)]);
    //   }
    //   });
}

login(): void {
    this.loading = true;
    this.authenticationService.login(this.model.username, this.model.password)
        .subscribe(
            response => {
              localStorage.setItem('token', response.headers.get('Authorization'));
              this.router.navigate([this.returnUrl]); }
        );
}

 
}
