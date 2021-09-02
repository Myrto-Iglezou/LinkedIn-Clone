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
  currentChat: Chat = null;
  chats: Chat[] = new Array<Chat>();
  incomingMessages: Message[] = new Array<Message>();
  outcomingMessages: Message[] = new Array<Message>();
  newMessage: Message = new Message();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authenticationService: AuthenticationService,
    private domSanitizer: DomSanitizer,
    private userService: UserService,
    private chatService: ChatService
  ) {  }

  ngOnInit() {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getUser(this.userDetails.id.toString()).subscribe(
      (user) => {
        Object.assign(this.user , user);
    },
      error => {
        alert(error.message);
      }
    );

    this.chatService.getChats(this.userDetails.id).subscribe(
      (chats) => {
        Object.assign(this.chats , chats);
    },
      error => {
        alert(error.message);
      }
    );

    this.chats = this.sortChatsByDate();
  }

  getOtherUser(chat:Chat): User {
    
    for (let u of chat.users) {
      if(u.id != this.userDetails.id)
        return u;
    }
    return null;
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

  sortChatsByDate(): Chat[] {
    return this.chats.sort(
      (a, b) => {
        return <any>new Date(b.timestamp) - <any>new Date(a.timestamp);
      }
    );
  }

  sortMessagesByDate(messages: Message[]): Message[]  {
    return messages.sort(
      (a, b) => {
        return <any>new Date(b.timestamp) - <any>new Date(a.timestamp);
      }
    );
  }

  openChat(chat:Chat) {
    this.currentChat = chat;
  }

  incomingMessagesFilter(chat: Chat) {

    this.currentChat.messages.forEach(
      m => {
        if(m.userMadeBy.id == this.user.id)
          this.outcomingMessages.push(m);
        else
          this.incomingMessages.push(m);
      }
    );
    this.outcomingMessages = this.sortMessagesByDate(this.outcomingMessages);
    this.incomingMessages = this.sortMessagesByDate(this.incomingMessages);
  }

  isIncoming(message: Message) {
    return message.userMadeBy.id != this.user.id;
  }

  addMessage(messageform) {
    if(messageform.form.valid) {
      this.newMessage.timestamp = new Date();
      this.chatService.newMessage(this.newMessage,this.userDetails.id,this.currentChat.id)
        .subscribe(
            response => {

              this.router.navigateByUrl('/SampleComponent', { skipLocationChange: true });
              this.router.navigate(["MessagingComponent"]);
            },
            error => {
              alert(error.message);
            }
        );
    }
  }
}