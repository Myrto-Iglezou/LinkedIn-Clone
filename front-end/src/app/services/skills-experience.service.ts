import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { SkillsAndExperience } from '../model/skills-experience';
import { User } from '../model/user';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class SkillsExperienceService {

  constructor(private http: HttpClient) {}

  addSkill(skillsexperience: SkillsAndExperience ,userId: number): Observable <HttpResponse<string>>{
    return this.http.put<string>('https://localhost:8443/in/' + userId.toString() + '/profile/new-info', skillsexperience, {observe : 'response'});
  }
  
}
