import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { Picture } from '../model/picture';
import { Post } from '../model/post';
import { UserDetails } from '../model/user-details';
import { FeedService } from '../services/feed.service';
import { DomSanitizer, SafeResourceUrl, SafeUrl} from '@angular/platform-browser';
import { User } from '../model/user';
import { InterestReaction } from '../model/interestReaction';
import { Comment } from '../model/comment';
import { UserService } from '../services/user.service';


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
  // commentText :string;
  newComments: Comment[] = new Array<Comment>();
  newComment = new Comment();
  newCom = new Comment();
  booleanButton=false;
  tempUser: User = new User();
  user: User = new User();
  sortType: number = 0;


  constructor(
    private feedService: FeedService, 
    private authenticationService: AuthenticationService,
    private router: Router,
    private domSanitizer: DomSanitizer,
    private userService: UserService 
  ) {}

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.feedService.getFeedPosts(this.userDetails.id).subscribe(
      (posts) => {
        Object.assign(this.posts , posts);
        if(this.sortType==0){
          this.posts = this.sortJobsByDate();
        }else if(this.sortType==1){
          this.sortJobsByRelevance();
        }
        this.posts.forEach(
          p => {
            p.newComment = new Comment();
            p.comments = this.sortCommentsByDate(p.comments);

          }
        );
      }
    );

    this.userService.getUser(this.userDetails.id.toString()).subscribe(
      (user) => {
        Object.assign(this.user , user);
    },
      error => {
        alert(error.message);
      }
    );
  }

  changeSort(num:number){
    if(num==0){
      this.sortJobsByDate();
    }else if(num==1){

    }
    this.sortType = num;
    location.reload();
  }

  sortJobsByDate(): Post[] {
    return this.posts.sort(
      (a, b) => {
        return <any>new Date(b.timestamp) - <any>new Date(a.timestamp);
      }
    );
  }

  sortJobsByRelevance() {
    

  }

  sortCommentsByDate(comments: Comment[]): Comment[] {
    return comments.sort(
      (a, b) => {
        return <any>new Date(a.timestamp) - <any>new Date(b.timestamp);
      }
    );
  }

  displayProfilePhoto(user: User): any{

    if(user.profilePicture) {
      if (user.profilePicture.type === 'image/png')
        return this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + user.profilePicture.bytes);
      else if (user.profilePicture.type === 'image/jpeg')
        return this.domSanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64,' + user.profilePicture.bytes);
    }
    return null;
  }

  displayPicture(pic: Picture): any{

    if(pic) {
      if (pic.type === 'image/png')
        return this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + pic.bytes);
      else if (pic.type === 'image/jpeg')
        return this.domSanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64,' + pic.bytes);
    }
    return null;
  }
  
  setUserInterested(post: Post) {
    this.feedService.addPostReaction(post.id,this.userDetails.id).subscribe(
      response => {
          location.reload();
        },
        error => {
          alert(error.message);
        }
    );
  }


  userIsInterested(post: Post): boolean {
    for(let i=0; i<post.interestReactions.length; i++){
      if(post.interestReactions[i].userMadeBy.id == this.userDetails.id)
        return true;
    }
    return false;
  
  }

  addNewComment(post: Post,commentform) {
    // alert("here");

    if(commentform.form.valid) {
      post.newComment.timestamp = new Date();
      this.feedService.addNewComment(this.userDetails.id,post.id,post.newComment)
        .subscribe(
          response => {
              location.reload();
            },
            error => {
              alert(error.message);
            }
        );
    }
    else{
      alert("Not valid data");
    }


  }
}
