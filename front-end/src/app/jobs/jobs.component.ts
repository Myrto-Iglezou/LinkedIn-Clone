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
        if(this.userDetails)
          this.router.navigate(['/in', this.userDetails.id.toString()]).then(() =>{
            location.reload();
          });
        else
          this.router.navigate(['/feed']).then(() => {
            location.reload();
          });
      }
    );
  }

  jobSubmit(jobForm){
    if(jobForm.form.valid){
      this.jobService.addJob(this.job,this.userDetails.id)
          .subscribe(
            responce => {},
              error => {
                alert(error.message);
              } 
          );
    }
    location.reload();

  }

}
