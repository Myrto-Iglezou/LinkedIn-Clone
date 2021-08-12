import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  @Output() messageEvent = new EventEmitter<string>();

  userId = localStorage.getItem('userID');
  userToken = localStorage.getItem('userToken');

  content: string;
  fileToUpload: File;
  image: string;
  fileName;

  postShared = false;
  alerts: string;
  type: 'success';

  postID;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient) { }

  ngOnInit() {

  }



}
