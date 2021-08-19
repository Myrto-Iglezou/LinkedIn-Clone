import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';
import { User } from '../model/user';
import { UserDetails } from '../model/user-details';
import { NetworkService } from '../services/network.service';

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {

  userQuery:string;
  userDetails: UserDetails;
  network: User[] = new Array<User>();
  searchResults: number= 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authenticationService: AuthenticationService,
    private networkService: NetworkService,
    private domSanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.networkService.getNetwork(this.userDetails.id).subscribe(
      (network) => {
        Object.assign(this.network , network);
      }
    );
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

  goToProfile(user: User) {  }

  search() { }
}
