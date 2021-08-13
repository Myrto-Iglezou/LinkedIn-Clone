import { Component, OnInit } from '@angular/core';
import { CreatePostComponent } from '../create-post/create-post.component';
import { UserDetails } from '../model/user-details';
import {AuthenticationService} from '../authentication.service';
import {ActivatedRoute, Router} from '@angular/router';


@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  userDetails: UserDetails = new UserDetails();

  constructor(private router: Router, private authenticationService: AuthenticationService, private route: ActivatedRoute) { }

  ngOnInit(): void {
  //   this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
  //     Object.assign(this.userDetails, userDetails);}

  // }
  }

}
