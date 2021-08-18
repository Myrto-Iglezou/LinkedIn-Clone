import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {

  userQuery:string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
  }

  search() {

  }
}
