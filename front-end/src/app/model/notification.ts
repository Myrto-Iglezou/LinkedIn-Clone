import { InterestReaction } from "./interestReaction";
import { User } from "./user";
import { Comment } from "./comment";
import { Connection } from "./connection";


export class Notification {
    id: number;
    isShown: boolean;
    type: string;
    userMadeBy: User;
    connection_request: Connection;
    new_comment: Comment;
    new_interest: InterestReaction;
  }