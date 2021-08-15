import { Photo } from './image';
import { Role } from './role';

export class User {
  id: number;
  name: string;
  surname: string;
  username: string;
  password: string;
  passwordConfirm: string;
  roles: Array<Role> = new Array<Role>();
  phoneNumber: string;
  profilePicture: Photo;
  city: string;
  currentJob: string;
  currentCompany: string;
  usersFollowing:  Array<User> = new Array<User>();
  userFollowedBy:  Array<User> = new Array<User>();
  numOfConnections: number;
  education: string;
  website: string;
  twitter: string;
  github: string;
  skills:[];
  // posts: Array<Post> = new Array<Post>();
  // comments: Array<Comment> = new Array<Comment>();
  // notifications: Array<Notification> = new Array<Notification>();
  // "interestReactions": [],
  // "jobsCreated": [],
  // "jobApplied": [],
  // "messages": [],
  // "chats": []

}
