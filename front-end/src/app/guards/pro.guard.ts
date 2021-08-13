import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {UserService} from '../user.service';
import {AuthenticationService} from '../authentication.service';
import { map } from 'rxjs/operators';
import {User} from '../model/user';
import {UserDetails} from '../model/user-details';
import {ErrorService} from '../error.service';

@Injectable({
  providedIn: 'root'
})
export class HostGuard implements CanActivate {

  user: User;
  userDetails: UserDetails;

  constructor(private userService: UserService, private authService: AuthenticationService, private errorService: ErrorService,private router: Router) {
  }


  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    this.authService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;

    });

    if(!this.userDetails){
      this.router.navigate(['/login'], {queryParams: { returnUrl: state.url } });
      return false;
    }
    else
      return this.userService.getUser(this.userDetails.id.toString()).pipe((
        map(
          user => {
            this.user = user;
            if( this.userHasRole('PROFESSIONAL')) {
              this.router.navigate(['/feed']);
              return  false;
            }
            else if( this.userHasRole('ADMIN')) {
              this.router.navigate(['/admin']);
              return  false;
            }
          }
        )
      ));
  }

  userHasRole(rolename: string): boolean{
    let flag = false;
    if (this.user) {
      this.user.roles.forEach((role) => {
        if (role.name === rolename) {
          flag = true;
        }
      });
    }
    return flag;
  }

}
