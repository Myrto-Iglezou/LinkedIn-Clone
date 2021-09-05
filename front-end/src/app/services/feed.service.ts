import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { Post } from '../model/post';
import { Comment } from '../model/comment';
import { InterestReaction } from '../model/interestReaction';


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

  getRecommendedPosts(userId: number): Observable <Post[]>{
    return this.http.get<Post[]>('https://localhost:8443/in/' + userId.toString() + '/recommended-posts');
  }

  addPostReaction(postId: number,userId: number): Observable<HttpResponse<any>>{
    return this.http.put<any>('https://localhost:8443/in/' + userId.toString() + '/feed/post-interested/'+ postId.toString(), {observe : 'response'});
  }

  addNewComment(userId: number,postId: number, comment: Comment): Observable<HttpResponse<any>>{
    return this.http.put<any>('https://localhost:8443/in/' + userId.toString() + '/feed/new-comment/'+ postId.toString(), comment, {observe : 'response'});
  }

  userIsInterested(userId: number,postId: number): Observable<HttpResponse<InterestReaction>> {
    return this.http.get<InterestReaction>('https://localhost:8443/in/' + userId.toString() + '/feed/is-interested/'+ postId.toString(), {observe : 'response'});
  }
}
