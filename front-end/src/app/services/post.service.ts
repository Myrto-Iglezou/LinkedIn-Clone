import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { Post } from '../model/post';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class CreatePostService {

  constructor(private http: HttpClient) {}

  // createNewPost(){}
}
