import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../services/user.service';
import {DomSanitizer} from '@angular/platform-browser';
import {Image} from '../model/image';
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
          this.router.navigate(['/homepage']).then(() => {
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

}
