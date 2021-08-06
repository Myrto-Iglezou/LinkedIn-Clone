import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { FormControl, Validators, FormGroup } from '@angular/forms';
// import {AuthenticationService} from '../../authentication.service';
import { User } from '../model/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  user: User = new User();

  constructor(
    private router: Router,
    // private authenticationService: AuthenticationService
  ) {}

  ngOnInit() {
    document.body.className = 'selector';
  }


  register(registerForm) {
    
    if (registerForm.form.valid) {

    }
  }
}
