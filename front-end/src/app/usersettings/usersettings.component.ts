import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { UserDetails } from '../model/user-details';
import {AuthenticationService} from './../authentication.service';
import { UserService } from '../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import { UserSettings } from '../model/usersettings';

@Component({
  selector: 'app-usersettings',
  templateUrl: './usersettings.component.html',
  styleUrls: ['./usersettings.component.css']
})
export class UsersettingsComponent implements OnInit {

  usersettings: UserSettings = new UserSettings();
  loading = false;
  dangerBox = false;
  successBox = false;
  submitattempt = false;
  submiterror: any ;
  submitmsg: string;
  currPswdInput: any = {};

  constructor(private userService: UserService, private route: ActivatedRoute , private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.userService.getUser(this.route.snapshot.paramMap.get('id')).subscribe((user) => {
      this.usersettings.id = user.id;
        this.usersettings.currentPassword = user.password;
        this.usersettings.currentUsername = user.username;
      }
    );
  }


  submit(usereditform){
    if (usereditform.form.valid  && (this.usersettings.newPassword === this.usersettings.passwordConfirm)) {
      this.loading = true;
      this.userService.editUserSettings(this.usersettings)
        .subscribe(
          response => {
            this.loading = false;
            const token = response.headers.get('Authorization');
            if (token) {
              let userDetails: UserDetails = null;
              this.authenticationService.getLoggedInUser().subscribe((uDetails) => {
                  userDetails = uDetails;
              });
              userDetails.token = token;
              this.authenticationService.setLoggedInUser(userDetails);
            }
            this.submitmsg = response.body;
            this.successBox = true;
            this.dangerBox = false;
          },
          error => {
            this.loading = false;
            this.submiterror = error;
            this.dangerBox = true;
            this.successBox = false;
            this.submitattempt = true;
          }
        );
    }
    else{
      this.submitattempt = true;
      this.dangerBox = false;
    }

  }

}
