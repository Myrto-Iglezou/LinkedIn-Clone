import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication.service';
import {NavigationEnd, Router} from '@angular/router';
import {UserDetails} from '../model/user-details';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userDetails: UserDetails;
  redirectUrl: string;

  constructor(private authService: AuthenticationService, private router: Router) { }

  ngOnInit(): void {
    this.authService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
      });
  }

  logout(){
    this.authService.logout();
  }

  makeRedirectUrl(userDetails: UserDetails): string {
    let redirectUrl: string = null;
    if (this.hasRole('PROFESSIONAL', userDetails)) redirectUrl = '/feed';
    else if (this.hasRole('ADMIN', userDetails)) redirectUrl = '/admin';
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

  goToProfile(){
    this.router.navigate(['/users/' + this.userDetails.id.toString()]).then(() => {
      location.reload();
    });
  }
}
