import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {

    return this.http.get<User[]>('https://localhost:8443/admin/users');
  }

}
