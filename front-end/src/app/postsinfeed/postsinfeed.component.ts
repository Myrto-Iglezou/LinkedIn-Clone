import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { Picture } from '../model/picture';
import { Post } from '../model/post';
import { UserDetails } from '../model/user-details';
import { FeedService } from '../services/feed.service';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';


@Component({
  selector: 'app-postsinfeed',
  templateUrl: './postsinfeed.component.html',
  styleUrls: ['./postsinfeed.component.css']
})
export class PostsinfeedComponent implements OnInit {

  userId = localStorage.getItem('userID');
  userToken = localStorage.getItem('userToken');
  page = 1;
  userDetails: UserDetails;
  posts: Post[] = new Array<Post>();

  constructor(
    private feedService: FeedService, 
    private authenticationService: AuthenticationService,
    private router: Router,
    private domSanitizer: DomSanitizer 
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

  displayPhoto(pic: Picture): any{

    if (pic.type === 'image/png') {
      // alert(this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + pic.bytes));
      return this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + pic.bytes);
    }
    else if (pic.type === 'image/jpeg') {
      return this.domSanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64,' + pic.bytes);
    }
    alert("null");
    return null;
  }

  newTab(photo: Picture){
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
}
