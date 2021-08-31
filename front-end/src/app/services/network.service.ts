import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { Post } from '../model/post';
import { Comment } from '../model/comment';
import { InterestReaction } from '../model/interestReaction';
import { User } from '../model/user';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class NetworkService {

  constructor(private http: HttpClient) {}

  addNewConnection(userId: number,connId: number): Observable<string> {
    return this.http.put<string>('https://localhost:8443/in/' + userId.toString() + '/new-connection/' + connId.toString(), {observe : 'response'});
  }

  getNetwork(userId: number): Observable<User[]> {
    return this.http.get<User[]>('https://localhost:8443/in/' + userId.toString() + '/network');
  }

  search(userId: number,searchQuery: string): Observable<User[]> {
    return this.http.get<User[]>('https://localhost:8443/in/' + userId.toString() + '/search/'+searchQuery);
  }

  hasSendRequest(mainUserId: number, otherUserId: number): Observable<boolean>{
    return this.http.get<boolean>('https://localhost:8443/in/' + mainUserId.toString() + '/request/'+otherUserId.toString());
  }

}
