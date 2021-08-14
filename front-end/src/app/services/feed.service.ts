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
export class FeedService {

  constructor(private http: HttpClient) {}

  addPost(formWrapper: FormData,userId: number): Observable<HttpResponse<string>>{
    return this.http.post<string>('https://localhost:8443/in/' + userId.toString() + '/feed/new-post', formWrapper, {observe : 'response'});
  }

  getFeedPosts(userId: number): Observable<Post[]>{
    return this.http.get<Post[]>('https://localhost:8443/in/' + userId.toString() + '/feed-posts');
  }

}
