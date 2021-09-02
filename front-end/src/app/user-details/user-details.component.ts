import { SkillsAndExperience } from './../model/skills-experience';
import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../services/user.service';
import {DomSanitizer} from '@angular/platform-browser';
import {AuthenticationService} from '../authentication.service';
import {UserDetails} from '../model/user-details';
import { Picture } from '../model/picture';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {SkillsExperienceService } from '../services/skills-experience.service';
import { NetworkService } from '../services/network.service';
import { Connection } from '../model/connection';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user: User = new User();
  skillsexperience: SkillsAndExperience = new SkillsAndExperience();
  validprofphoto = true;
  changeButton = false;
  userDetails: UserDetails;
  connectedUser: User = new User();
  userNetwork: User[] = new Array<User>();
  requestConnectButton = false;
  connectPendingButton = false;
  connectedButton = false;
  networkButton = false;

  usersFollowing:  Connection[] = new Array<Connection>();
  userFollowedBy:  Connection[] = new Array<Connection>();

  closeResult = '';


  constructor(
    private route: ActivatedRoute,
    private router: Router, 
    private userService: UserService, 
    private domSanitizer: DomSanitizer, 
    private authService: AuthenticationService,
    private modalService: NgbModal,
    private skillsexperienceService:SkillsExperienceService,
    private networkService: NetworkService,
    ) { }

  ngOnInit(): void {
    this.authService.getLoggedInUser().subscribe((userDetails) => {
      this.userDetails = userDetails;
    });

    this.userService.getOtherUser(this.userDetails.id.toString(),this.route.snapshot.paramMap.get('id').toString()).subscribe((user) => {
        Object.assign(this.user , user);
      },
      error => {
        alert(error.message);
      }
    );

    this.userService.getUser(this.userDetails.id.toString()).subscribe((user1) => {
      Object.assign(this.connectedUser , user1);
    },
      error => {
        alert(error.message);
      }
    );

    this.networkService.getNetwork(this.userDetails.id).subscribe(
      (network) => {
        Object.assign(this.userNetwork , network);
      }
    );
  }

  connected(id: number): boolean {

    for (let u of this.userNetwork) {
      if(u.id == id)
        return true;
    }
      return false;    
  }

  hasRequestPending(id: number): boolean {

    let flag=false;

    this.connectedUser.usersFollowing.forEach(
      con => {
        if(con.userFollowed.id == this.user.id && !con.isAccepted)
          flag=true;
      }
    );

    this.connectedUser.userFollowedBy.forEach(
      con => {
        if(con.userFollowing.id == this.user.id && !con.isAccepted)
          flag=true;
      }
    );

    return flag;
  }

  getRoles(){
    let str = '';
    this.user.roles.forEach((role) => {
      if(role.name === 'ROLE_ADMIN')
        str = str + 'Admin,';
      else if(role.name === 'ROLE_PRO')
        str = str + 'Pro,';
      });

    return str.slice(0, -1);
  }

  displayProfilePhoto(): any{
    if(this.user.profilePicture) {
      if (this.user.profilePicture.type === 'image/png')
        return this.domSanitizer.bypassSecurityTrustUrl('data:image/png;base64,' + this.user.profilePicture.bytes);
      else if (this.user.profilePicture.type === 'image/jpeg')
        return this.domSanitizer.bypassSecurityTrustUrl('data:image/jpeg;base64,' + this.user.profilePicture.bytes);
    }
    return null;
  }
    
  hasRole(rolename: string): boolean {
    let flag = false;
    if (this.userDetails) {
      this.userDetails.roles.forEach((role) => {
        if (role === rolename) flag = true;
      });
    }
    return flag;
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  informSkillsAndExperience(skillsForm,model: string){
    if(skillsForm.form.valid) {
      // modal.close('Save click');
      if(model == 'EXPERIENCE'){
        this.skillsexperience.type = 'EXPERIENCE';
      }else if(model == 'SKILL'){
        this.skillsexperience.type = 'SKILL';
      }else if(model == 'EDUCATION'){
        this.skillsexperience.type = 'EDUCATION';
      }

      this.skillsexperienceService.addSkill(this.skillsexperience,this.userDetails.id)
        .subscribe(
          responce => {},
            error => {
              alert(error.message);
            } 
        );
    }
    location.reload();
  }

  addConnection(user: User) {
    this.networkService.addNewConnection(this.userDetails.id,user.id).subscribe(
      responce => {},
      error => {
        alert(error.message);
      }      
    );     
    
    this.networkService.getNetwork(this.userDetails.id).subscribe(
      (network) => {
        Object.assign(this.userNetwork , network);
      }
    );

    location.reload();
  }

  isAdmin(user: User){
    for (let role of user.roles){
      if(role.name == 'ADMIN')
        return true;
    }
    return false;
  }

  changeJobStatus(editform){
    if(editform.form.valid) {
      this.userService.editUserJob(this.user)
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
