import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { Post } from '../model/post';
import { Comment } from '../model/comment';
import { InterestReaction } from '../model/interestReaction';
import { Chat } from '../model/chat';
import { Message } from '../model/message';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) {}

  newMessage(message: Message,userId: number,chatId: number): Observable<HttpResponse<string>>{
    return this.http.post<string>('https://localhost:8443/in/' + userId.toString() + '/chat/'+chatId.toString()+'/new-message', message, {observe : 'response'});
  }

  getChats(userId: number): Observable<Chat[]>{
    return this.http.get<Chat[]>('https://localhost:8443/in/' + userId.toString() + '/chats');
  }

}
