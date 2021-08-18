import { InterestReaction } from "./interestReaction";
import { User } from "./user";
import { Comment } from "./comment";


export class Notification {
    id: number;
    isShown: boolean;
    type: string;
    userMadeBy: User;
    connection_request: User;
    new_comment: Comment;
    new_interest: InterestReaction;
  }