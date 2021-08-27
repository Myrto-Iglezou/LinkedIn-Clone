import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class SkillsExperienceService {

  constructor(private http: HttpClient) {}

  addSkill(formWrapper: FormData,userId: number): Observable <HttpResponse<string>>{
    return this.http.post<string>('https://localhost:8443/in/' + userId.toString() + '/profile/new-info', formWrapper, {observe : 'response'});
  }
}
