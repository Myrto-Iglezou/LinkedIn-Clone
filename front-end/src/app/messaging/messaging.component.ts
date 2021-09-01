import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { User } from '../model/user';
import { UserDetails } from '../model/user-details';
import { UserService } from '../services/user.service';
import { ChatService } from '../services/chat.service';
import { Chat } from "../model/chat";
import { Message } from '../model/message';

@Component({
  selector: 'app-messaging',
  templateUrl: './messaging.component.html',
  styleUrls: ['./messaging.component.css']
})
export class MessagingComponent implements OnInit {

  user: User = new User();
  userDetails: UserDetails;
  openedChat: Chat;
  chats: Chat[];
  messages: Message[] = new Array<Message>();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authenticationService: AuthenticationService,
    private domSanitizer: DomSanitizer,
    private userService: UserService,
    private ChatService: ChatService
  ) {  }

  ngOnInit() {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getUser(this.userDetails.id.toString()).subscribe(
      (user) => {
        Object.assign(this.user , user);
        Object.assign(this.chats , user.chats);
    },
      error => {
        alert(error.message);
      }
    );

    this.sortedChats();
  }

  sortedChats() {
    this.chats = this.chats.sort(
      (a, b) => {
        return <any>new Date(b.timestamp) - <any>new Date(a.timestamp);
      }
    );
  }

  incomingMessagesFilter(chat: Chat) {

    // chat.chatMessages

  }

  

}