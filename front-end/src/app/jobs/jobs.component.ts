import { Component, OnInit } from '@angular/core';
import { Job } from '../model/job';
import { User } from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../services/user.service';
import {AuthenticationService} from '../authentication.service';
import {UserDetails} from '../model/user-details';
import { JobsService } from '../services/jobs.service';

@Component({
  selector: 'app-jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['./jobs.component.css']
})
export class JobsComponent implements OnInit {

  user: User = new User();
  userDetails: UserDetails;
  job: Job = new Job();
  jobs: Job[] = new Array<Job>();
  recommendedJobs: Job[] = new Array<Job>();
  sortType: number=0;

  constructor(
    private route: ActivatedRoute,
    private router: Router, 
    private userService: UserService, 
    private authService: AuthenticationService,
    private jobService: JobsService,
  ) { }

  ngOnInit(): void {
    this.authService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getUser(this.userDetails.id.toString()).subscribe(
      (user) => {
        Object.assign(this.user , user);
      },
      error => {
        alert(error.message);
      }
    );
    
    this.jobService.getRecommendedJobs(this.userDetails.id).subscribe(
      (jobs) => {
        if(jobs.length != 0)
          Object.assign(this.recommendedJobs , jobs);
      },
      error => {
        alert(error.message);
      }
    );  

    this.jobService.getJobs(this.userDetails.id).subscribe(
      (jobs) => {
        if(this.sortType==0 || this.sortType==null){
          if(this.jobs.length!=0)
            this.jobs = new Array<Job>();
          Object.assign(this.jobs , jobs);
          this.sortJobsByDate();
        }else if(this.sortType==1){
          if(this.jobs.length!=0)
            this.jobs = new Array<Job>();
          Object.assign(this.jobs,this.recommendedJobs);
        }
      },
      error => {
        alert(error.message);
      }
    );
  }

  changeSort(num:number){
    
    this.sortType = num;
    this.ngOnInit();
  }

  sortJobsByDate() {
    this.jobs.sort(
      (a, b) => {
        return <any>new Date(b.timestamp) - <any>new Date(a.timestamp);
      }
    );
  }


  jobSubmit(jobForm){
    if(jobForm.form.valid){
      this.job.timestamp = new Date();
      this.jobService.addJob(this.job,this.userDetails.id).subscribe(
        responce => {
          this.ngOnInit();
        },
        error => {
          alert(error.message);
        } 
      );
    }
  }

  alreadyApplied(job: Job): boolean{

    for (let u of job.usersApplied) {
      if(u.id == this.userDetails.id)
        return true;
    }
    return false;
 
  } 

  newApplication(jobId: number) {
    this.jobService.apply(jobId,this.userDetails.id).subscribe(
      responce => {
        this.ngOnInit();
      },
      error => {
        alert(error.message);
      } 
    );
  }

  usersJob(job: Job) {
    if(job.userMadeBy.id == this.user.id)
      return true;
    else 
      return false;  
    }

}
