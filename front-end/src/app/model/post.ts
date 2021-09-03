import { User } from "./user";
import { Comment } from "./comment";
import { InterestReaction } from "./interestReaction";
import { Picture } from "./picture";

export class Post{
    id: number;
    content: string;
    owner: User;
    timestamp: Date;
    comments: Array<Comment> = new Array<Comment>();
    interestReactions: Array<InterestReaction> = new Array<InterestReaction>();
    pictures: Array<Picture> = new Array<Picture>();
    newComment: Comment;
}