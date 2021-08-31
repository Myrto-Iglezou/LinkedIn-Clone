import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication.service';
import {NavigationEnd, Router} from '@angular/router';
import {ActivatedRoute} from '@angular/router';
import {UserDetails} from '../model/user-details';
import { User } from '../model/user';
import { UserService } from '../services/user.service';
import { PostsinfeedComponent } from '../postsinfeed/postsinfeed.component';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userDetails: UserDetails;
  redirectUrl: string;
  user: User = new User();

  constructor(private authService: AuthenticationService, private router: Router,private userService: UserService, private route: ActivatedRoute, private domSanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.authService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
      });
    
    this.userService.getUser(this.userDetails.id.toString()).subscribe((user) => {
        Object.assign(this.user , user);
      },
      error => {
        if(this.userDetails)
          this.router.navigate(['/in', this.userDetails.id.toString()]).then(() =>{
            location.reload();
          });
        else
          this.router.navigate(['/feed']).then(() => {
            location.reload();
          });
      }
    );
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

  displayProfilePhoto(): any{
    if(this.user.profilePicture) {
      if (this.user.profilePicture.type === 'image/png')
        return this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + this.user.profilePicture.bytes);
      else if (this.user.profilePicture.type === 'image/jpeg')
        return this.domSanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64,' + this.user.profilePicture.bytes);
    }
    return null;
  }
}
