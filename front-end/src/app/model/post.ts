import { User } from "./user";
import { Comment } from "./comment";

export class Post{
    id: number;
    content: string;
    owner: User;
    date: Date;
    comments: Array<Comment> = new Array<Comment>();
}