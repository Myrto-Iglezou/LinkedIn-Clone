import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { UserSettings } from '../model/usersettings';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}

   getUser(id: string): Observable<User> { 
    return this.http.get<User>('https://localhost:8443/in/' + id);
  }

  getOtherUser(id: string,otherId: string): Observable<User> {
    return this.http.get<User>('https://localhost:8443/in/' + id + '/user-profile/'+ otherId);
  }

  editUserSettings(usersettings: UserSettings): Observable<HttpResponse<any>>{
    return this.http.put<UserSettings>('https://localhost:8443/in/' + usersettings.id.toString() + '/settings', usersettings, {observe : 'response'});
  }

  changeUsername(usersettings: UserSettings): Observable<HttpResponse<any>>{
    return this.http.put<UserSettings>('https://localhost:8443/in/' + usersettings.id.toString() + '/settings/change-username', usersettings, {observe : 'response'});
  }

  changePassword(usersettings: UserSettings): Observable<HttpResponse<any>>{
    return this.http.put<UserSettings>('https://localhost:8443/in/' + usersettings.id.toString() + '/settings/change-password', usersettings, {observe : 'response'});
  }

  editUserJob(user:User):  Observable<HttpResponse<any>>{
    return this.http.put<UserSettings>('https://localhost:8443/in/' + user.id.toString() + '/editJob',user, {observe : 'response'});
  }

}
