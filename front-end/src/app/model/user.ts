import { Picture } from './picture';
import { Role } from './role';
import { SkillsAndExperience } from './skills-experience';

export class User {
  id: number;
  name: string;
  surname: string;
  username: string;
  password: string;
  passwordConfirm: string;
  roles: Array<Role> = new Array<Role>();
  phoneNumber: string;
  profilePicture: Picture;
  city: string;
  currentJob: string;
  currentCompany: string;
  usersFollowing:  Array<User> = new Array<User>();
  userFollowedBy:  Array<User> = new Array<User>();
  numOfConnections: number;
  education: Array <SkillsAndExperience> = new Array <SkillsAndExperience>();
  workExperience: Array <SkillsAndExperience> = new Array <SkillsAndExperience>();
  skills: Array <SkillsAndExperience> = new Array <SkillsAndExperience>();
  website: string;
  twitter: string;
  github: string;
  // posts: Array<Post> = new Array<Post>();
  // comments: Array<Comment> = new Array<Comment>();
  // notifications: Array<Notification> = new Array<Notification>();
  // "interestReactions": [],
  // "jobsCreated": [],
  // "jobApplied": [],
  // "messages": [],
  // "chats": []

}
