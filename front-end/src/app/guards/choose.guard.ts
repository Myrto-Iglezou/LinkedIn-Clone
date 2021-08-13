import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {UserDetails} from '../model/user-details';
import {AuthenticationService} from '../authentication.service';

@Injectable({
  providedIn: 'root'
})
export class ChooseGuard implements CanActivate {
  userDetails: UserDetails;

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    this.authService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });
    if (!this.userDetails) {
      this.router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
      return false;
    }
    if (this.hasRole('PROFESSIONAL') && this.hasRole('ADMIN'))
      return true;
    else if (this.hasRole('PROFESSIONAL')) {
      this.router.navigate(['/feed']);
      return false;
    }
    else if (this.hasRole('ADMIN')) {
      this.router.navigate(['/admin']);
      return false;
    }
  }

  hasRole(rolename: string): boolean{
    let flag = false;
    if (this.userDetails) {
      this.userDetails.roles.forEach((role) => {
        if (role === rolename) {
          flag = true;
        }
      });
    }
    return flag;
  }

}
