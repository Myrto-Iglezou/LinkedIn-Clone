import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { Post } from '../model/post';
import { UserDetails } from '../model/user-details';
import { FeedService } from '../services/feed.service';

@Component({
  selector: 'app-postsinfeed',
  templateUrl: './postsinfeed.component.html',
  styleUrls: ['./postsinfeed.component.css']
})
export class PostsinfeedComponent implements OnInit {

  userId = localStorage.getItem('userID');
  userToken = localStorage.getItem('userToken');

  userDetails: UserDetails;
  posts: Post[] = new Array<Post>();

  constructor(
    private feedService: FeedService, 
    private authenticationService: AuthenticationService,
    private router: Router 
  ) {}

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.feedService.getFeedPosts(this.userDetails.id).subscribe(
      (posts) => {
        Object.assign(this.posts , posts);
      }
    );
  }

}
