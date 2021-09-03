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
  interval;

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
        
        this.sortChatsByDate();
       
        // this.loadChatRoom();
        // this.interval = setInterval(() => { this.loadChatRoom(); }, 30000);
    },
      error => {
        alert(error.message);
      }
    );

    
    
  }

  sortChatsByDate() {
    // alert(this.chats.length-1);
    this.chats.forEach(
      c => {
        if(c.messages.length!=0){
          c.messages = this.sortMessagesByDate(c.messages);
          c.timestamp = c.messages[c.messages.length-1].timestamp;
          c.latestMessage = c.messages[c.messages.length-1];
          // alert(c.timestamp);
        }
      }
    );


    this.chats.sort(
      (a, b) => {
        return <any>new Date(b.timestamp) - <any>new Date(a.timestamp);
      }
    );
      
    this.currentChat = this.chats[0];


  }

  sortMessagesByDate(messages: Message[]): Message[]  {
    return messages.sort(
      (a, b) => {
        return <any>new Date(a.timestamp) - <any>new Date(b.timestamp);
      }
    );
  }


  ngOnDestroy() {
    clearInterval(this.interval);
  }

  getActiveChats() {
    this.chatService.getChats(this.userDetails.id).subscribe(
      (chats) => {
        Object.assign(this.chats , chats);
        this.loadChatRoom();
        this.interval = setInterval(() => { this.loadChatRoom(); }, 30000);
    },
      error => {
        alert(error.message);
      }
    );
  }

  loadChatRoom() {
    // this.getActiveChats();
    this.openChat(this.currentChat);
  }


  getOtherUser(chat:Chat): User {
    
    for (let u of chat.users) {
      if(u.id != this.userDetails.id)
        return u;
    }
    return null;
  }

  reloadCurrentRoute() {
    let currentUrl = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.router.navigate([currentUrl]);
    });
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
              // this.currentChat.messages.push(this.newMessage);
              location.reload();
              // document.getElementById("selectButton-{{currentChat.id}}").click();
                            // this.reloadCurrentRoute();
            },
            error => {
              alert(error.message);
            }
        );
    }
  }

  goToProfile(user: User) {
    this.router.navigate(['/users/' + user.id.toString()]).then(() => {
      location.reload();
    });   
  }
}