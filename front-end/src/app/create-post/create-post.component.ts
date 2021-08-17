import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '../model/post';
import { FeedService } from '../services/feed.service';
import { AuthenticationService } from '../authentication.service';
import { UserDetails } from '../model/user-details';
import { PostsinfeedComponent } from '../postsinfeed/postsinfeed.component';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  @Output() updateView = new EventEmitter<string>();

  userId = localStorage.getItem('userID');
  userToken = localStorage.getItem('userToken');
  newPost = new Post();

  loading = false;
  dangerBox = false;
  successBox = false;
  submitattempt = false;
  submiterror: any ;
  submitmsg: string;
  mainPhoto: File;
  photos = new Array<File>();
  validphotos = true;
  userDetails: UserDetails;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authenticationService: AuthenticationService,
    private feedService: FeedService,
    // private postsinFeedComponent: PostsinfeedComponent
  ) { }

  ngOnInit() {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });
  }

  // share() {
  //   this.sendRefreshMessage();
  // }

  sendRefreshMessage() {
    this.updateView.emit('1');
  }

  setPhotos(inputElement){
    let count = 0 ;

    for ( let i = 0 ; i < inputElement.files.length ; i = i + 1) {
      this.photos[i] = inputElement.files[i];
      if (this.photos[i].type === 'image/jpeg' || this.photos[i].type === 'image/png') {
        count = count + 1;
      }
    }

    if (count === inputElement.files.length) {
      this.validphotos = true;
    }
    else {
      this.validphotos = false;
    }
  }

  createNewPost(postform){
    // alert("here");

    if(postform.form.valid) {
      this.newPost.timestamp = new Date();
      const formWrapper = new FormData();
      const postBlob = new Blob([JSON.stringify(this.newPost)], { type: 'application/json'});
      if (this.mainPhoto) {
        formWrapper.append('imageFile' , this.mainPhoto , 'mainPhoto');
      }
      this.photos.forEach(
        (photo) => {
          if (photo) {
            formWrapper.append('imageFile', photo, 'extraPhoto');
          }
        }
      );

      formWrapper.append('object', postBlob );
      this.loading = true;
      this.feedService.addPost(formWrapper,this.userDetails.id)
        .subscribe(
          response => {
            // alert(this.userDetails.id);
            this.sendRefreshMessage();
            //  this.postsinFeedComponent.ngOnInit();
             this.loading = false;
             this.submitmsg = response.body;
             this.successBox = true;
             this.dangerBox = false;
             this.updateView.emit();
             location.reload();
            //  alert("emit made");
            },
            error => {
              this.loading = false;
              this.submiterror = error;
              this.dangerBox = true;
              this.successBox = false;
              this.submitattempt = true;
              alert(error.message);
            }
        );
    }
    else{
      this.submitattempt = true;
      this.dangerBox = false;
      this.successBox = false;
    }


  }


}
