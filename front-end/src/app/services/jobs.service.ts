import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Job } from '../model/job';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobsService {

  constructor(private http: HttpClient) {}

  addJob(job: Job ,userId: number): Observable <HttpResponse<string>>{
    alert(job.title);
    return this.http.post<string>('https://localhost:8443/in/' + userId.toString() + '/new-job', job, {observe : 'response'});
  }
}
