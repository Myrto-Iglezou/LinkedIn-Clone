import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../services/user.service';
import {DomSanitizer} from '@angular/platform-browser';
import {Photo} from '../model/image';
import {AuthenticationService} from '../authentication.service';
import {UserDetails} from '../model/user-details';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User = new User();
  validprofphoto = true;
  changeButton = false;
  userDetails: UserDetails;

  requestConnectButton = false;
  connectPendingButton = false;
  connectedButton = false;
  networkButton = false;

  hideElements = false;


  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private domSanitizer: DomSanitizer, private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.authService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getUser(this.route.snapshot.paramMap.get('id')).subscribe((user) => {
        Object.assign(this.user , user);
      },
      error => {
        if(this.userDetails)
          this.router.navigate(['/users', this.userDetails.id.toString()]).then(() =>{
            location.reload();
          });
        else
          this.router.navigate(['/feed']).then(() => {
            location.reload();
          });
      }
    );
  }

  getRoles(){
    let str = '';
    this.user.roles.forEach((role) => {
      if(role.name === 'ROLE_ADMIN')
        str = str + 'Admin,';
      else if(role.name === 'ROLE_PRO')
        str = str + 'Pro,';
      });

    return str.slice(0, -1);
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

  newTab(photo: Photo ){
    const image = new Image();
    if (photo.type === 'image/png') {
      image.src = 'data:image/png;base64,' + photo.bytes;
    }
    else if (photo.type === 'image/jpeg') {
      image.src = 'data:image/jpeg;base64,' + photo.bytes;
    }

    const w = window.open(  '_blank');
    w.document.write(image.outerHTML);
  }

    
  hasRole(rolename: string): boolean {
    let flag = false;
    if (this.userDetails) {
      this.userDetails.roles.forEach((role) => {
        if (role === rolename) flag = true;
      });
    }
    return flag;
  }
}
