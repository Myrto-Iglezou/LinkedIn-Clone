import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, ActivatedRoute} from '@angular/router';
import { Observable } from 'rxjs';
import {UserDetails} from '../model/user-details';
import {AuthenticationService} from '../authentication.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

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
    else if(Number(next.paramMap.get('id')) !== this.userDetails.id) {
      const str = state.url.substring(7, state.url.length);
      this.router.navigate(['/feed/' + this.userDetails.id.toString() + str.substring(str.indexOf('/', 0 ))]);
      return false;
    }
    else
      return true;


  }



}
