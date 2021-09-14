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
  loading_1 = false;
  loading_2 = false;
  dangerBox_1 = false;
  dangerBox_2 = false;
  successBox_1 = false;
  successBox_2 = false;
  submitattempt_1 = false;
  submitattempt_2 = false;
  submiterror: any ;
  submitmsg: string;
  currPwd_1: string; 
  currPwd_2: string; 
  userDetails: UserDetails;
  user: User;

  constructor(private userService: UserService, private route: ActivatedRoute , private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getUser(this.userDetails.id.toString()).subscribe((user) => {
        this.user = user;
        this.usersettings.id = user.id;
        this.usersettings.currentPassword = user.password;
        this.usersettings.currentUsername = user.username;
        this.usersettings.newPassword = null;
        this.usersettings.passwordConfirm = null;
        this.usersettings.newUsername = null;
      }
    );
  }


  emailSubmit(userform){
    if (userform.form.valid) {
      this.loading_1 = true;

      this.usersettings.newPassword = null;
      this.usersettings.passwordConfirm = null;
      this.usersettings.currentPassword = this.currPwd_1;

      this.userService.changeUsername(this.usersettings)
        .subscribe(
          response => {
            this.loading_1 = false;
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
            this.successBox_1 = true;
            this.dangerBox_1 = false;
          },
          error => {
            this.loading_1 = false;
            this.submiterror = error;
            this.dangerBox_1 = true;
            this.successBox_1 = false;
            this.submitattempt_1 = true;
          }
        );
    }
    else{
      this.submitattempt_1 = true;
      this.dangerBox_1 = false;
    }
  }



  pwdSubmit(userform){
    if (userform.form.valid  && (this.usersettings.newPassword === this.usersettings.passwordConfirm)) {
      this.loading_2 = true;
      this.usersettings.newUsername = null;
      this.usersettings.currentUsername = this.user.username;
      this.usersettings.currentPassword = this.currPwd_2;
      this.userService.changePassword(this.usersettings)
        .subscribe(
          response => {
            this.loading_2 = false;
            this.submitmsg = response.body;
            this.successBox_2 = true;
            this.dangerBox_2 = false;
          },
          error => {
            this.loading_2 = false;
            this.submiterror = error;
            this.dangerBox_2 = true;
            this.successBox_2 = false;
            this.submitattempt_2 = true;
          }
        );
    }
    else{
      this.submitattempt_2 = true;
      this.dangerBox_2 = false;
    }

  }

}
