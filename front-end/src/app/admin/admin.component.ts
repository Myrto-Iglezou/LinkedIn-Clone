import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import { AdminService } from '../services/admin.service';
import {AuthenticationService} from '../authentication.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDetails } from '../model/user-details';

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
    this.adminService.getUsers().subscribe(
      users => this.users = users
    );
    
  }

  addToList(user: User){
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
}
