import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { User } from '../model/user';
import { UserDetails } from '../model/user-details';
import { NetworkService } from '../services/network.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {

  startPage : number;
  paginationLimit:number; 
  userQuery:string;
  userDetails: UserDetails;
  network: User[] = new Array<User>();
  searchResults: User[] = new Array<User>();
  user: User = new User();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authenticationService: AuthenticationService,
    private networkService: NetworkService,
    private domSanitizer: DomSanitizer,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getUser(this.userDetails.id.toString()).subscribe((user1) => {
      Object.assign(this.user , user1);
    },
      error => {
        alert(error.message);
      }
    );

    this.networkService.getNetwork(this.userDetails.id).subscribe(
      (network) => {
        Object.assign(this.network , network);
      }
    );

    this.startPage = 0;
    this.paginationLimit = 8;
  }

  addConnection(user: User) {
    this.networkService.addNewConnection(this.userDetails.id,user.id).subscribe(
      responce => {},
      error => {
        alert(error.message);
      }      
    );

    this.networkService.getNetwork(this.userDetails.id).subscribe(
      (network) => {
        Object.assign(this.network , network);
      }
    );

    this.reloadCurrentRoute();
      
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

  goToProfile(user: User) {
    this.router.navigate(['/users/' + user.id.toString()]).then(() => {
      location.reload();
    });   
  }

  startConversation(user: User) {
    // alert("here");
    this.router.navigate(['/messaging/' + user.id.toString()]).then(() => {
      location.reload();
    });
  }

  connected(id: number): boolean {
    for (let u of this.network) {
      if(u.id == id)
        return true;
    }
    return false;    
  }

  hasRequestPending(id: number): boolean {

    let flag=false;

    this.user.usersFollowing.forEach(
      con => {
        if(con.userFollowed.id == id && !con.isAccepted)
          flag=true;
      }
    );

    this.user.userFollowedBy.forEach(
      con => {
        if(con.userFollowing.id == id && !con.isAccepted)
          flag=true;
      }
    );

    return flag;
  }

   
  showMoreItems(){
      this.paginationLimit = Number(this.paginationLimit) + 4;        
  }

  search() {
    
    // this.searchResults = null;
    this.networkService.search(this.userDetails.id,this.userQuery).subscribe(
      (searchResults) => {
        Object.assign(this.searchResults , searchResults);
      }
    );
    this.ngOnInit();

  }
}
