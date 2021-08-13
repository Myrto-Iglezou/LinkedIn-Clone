import { Image } from './image';
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
  profilePicture: Image;
  city: string;
  currentJob: string;
  currentCompany: string;
  usersFollowing:  Array<User> = new Array<User>();
  userFollowedBy:  Array<User> = new Array<User>();
  // posts: Array<Post> = new Array<Post>();
  // comments: Array<Comment> = new Array<Comment>();
  // notifications: Array<Notification> = new Array<Notification>();
  // "interestReactions": [],
  // "jobsCreated": [],
  // "jobApplied": [],
  // "messages": [],
  // "chats": []

}
