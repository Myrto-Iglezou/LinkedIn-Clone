import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { Post } from '../model/post';
import { Comment } from '../model/comment';
import { Notification } from '../model/notification';
import { InterestReaction } from '../model/interestReaction';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  constructor(private http: HttpClient) {}

  acceptConnection(userId: number,connId: number): Observable<string>{
    return this.http.put<string>('https://localhost:8443/in/' + userId.toString() + '/notifications/' + connId.toString() + '/accept-connection', {observe : 'response'});
  }

  getNotifications(userId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>('https://localhost:8443/in/' + userId.toString() + '/notifications');
  }


}
