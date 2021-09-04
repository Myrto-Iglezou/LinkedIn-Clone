import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import { AdminService } from '../services/admin.service';
import {AuthenticationService} from '../authentication.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDetails } from '../model/user-details';
import * as JsonToXML from 'js2xmlparser';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  users: User[] = new Array<User>();
  paginationLimit:number; 
  startPage : number;
  usersToExtract: User[] = new Array<User>();
  downloadAttempt: boolean;

  constructor(
    private adminService: AdminService,
    private authService: AuthenticationService,
    private router: Router,
    private domSanitizer: DomSanitizer,   
    ){}

  ngOnInit(): void {
    this.getUsers();
    this.startPage = 0;
    this.paginationLimit = 12;
    this.downloadAttempt = false;
    this.adminService.getUsers().subscribe(
      users => this.users = users
    );
    
  }

  addToList(user: User){
    let flag = false;
    this.usersToExtract.forEach( (item, index) => {
      if(item === user) {
        this.usersToExtract.splice(index,1);
        flag = true
      }
    });
    if(flag == false)
      this.usersToExtract.push(user);
  }

  getUsers(): void {
    this.adminService.getUsers().subscribe(users => this.users = users);

  }

  logout(){
    this.authService.logout();
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

  goToProfile(user: User) {
    this.router.navigate(['/users/' + user.id.toString()]).then(() => {
      location.reload();
    });   
  }

  isAdmin(user: User){
    for (let role of user.roles){
      if(role.name == 'ADMIN')
        return true;
    }
    return false;
  }

  showMoreItems(){
    this.paginationLimit = Number(this.paginationLimit) + 4;        
  }

  downloadUSers(type: string, element){   

    this.downloadAttempt = true;
    if(this.usersToExtract.length != 0){

      if(type == 'xml'){
        var JSONfile = JSON.stringify(this.usersToExtract);
        JSONfile = JSON.parse(JSONfile)
        var js2xmlparser = require("js2xmlparser");
        let XMLfile = js2xmlparser.parse("user",JSONfile);
            
        element.setAttribute('href', 'data:text/' + type + ';charset=UTF-8,' + XMLfile);  
      
        element.setAttribute('download', 'users.' + type);
        element.click();
      }else{
        element.setAttribute('href', 'data:text/' + type + ';charset=UTF-8,' + encodeURIComponent(JSON.stringify(this.usersToExtract)));
        element.setAttribute('download', 'users.' + type);
        element.click();
      }
    }
  }

}
