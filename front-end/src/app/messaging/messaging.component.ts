import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { User } from '../model/user';
import { UserDetails } from '../model/user-details';
import { UserService } from '../services/user.service';
import { Chat } from "../model/chat";

@Component({
  selector: 'app-messaging',
  templateUrl: './messaging.component.html',
  styleUrls: ['./messaging.component.css']
})
export class MessagingComponent implements OnInit {

  user: User = new User();
  userDetails: UserDetails;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authenticationService: AuthenticationService,
    private domSanitizer: DomSanitizer,
    private userService: UserService
    ) {
  }

  ngOnInit() {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getUser(this.userDetails.id.toString()).subscribe((user) => {
      Object.assign(this.user , user);
    },
      error => {
        alert(error.message);
      }
    );
  }


}