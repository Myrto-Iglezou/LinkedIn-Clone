import { Post } from "./post";
import { User } from "./user";

export class InterestReaction {
    id: number;
    userMadeBy: User;
    post: Post;
  }