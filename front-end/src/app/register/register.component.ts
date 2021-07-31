import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {FormControl, Validators,FormGroup} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm = new FormGroup({
    firstname : new FormControl('', [Validators.required]),
    lastname : new FormControl('', [Validators.required]),
    phonenumber : new FormControl('', [Validators.required]),
    email : new FormControl('', [Validators.required, Validators.email]),
    password_1 : new FormControl('', [Validators.required]),
    password_2 : new FormControl('', [Validators.required]),
    image : new FormControl('', [Validators.required]),
    
  });

  constructor() { }

  ngOnInit() {
    document.body.className = "selector";
  }

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.registerForm.value);
  }
}
