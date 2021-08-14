import { Component, OnInit } from '@angular/core';
import { CreatePostComponent } from '../create-post/create-post.component';
import { UserDetails } from '../model/user-details';
import {AuthenticationService} from '../authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import { Post } from '../model/post';
import { User } from '../model/user';
import { UserService } from '../services/user.service';


@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

  posts: Post[] = new  Array<Post>();
  user: User = new User();
  userDetails: UserDetails;

  constructor(private router: Router, private authenticationService: AuthenticationService, private userService: UserService, private route: ActivatedRoute/* , private postService: PostService */ ) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getUser(this.userDetails.id.toString()).subscribe(
      (user) => {
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

}
