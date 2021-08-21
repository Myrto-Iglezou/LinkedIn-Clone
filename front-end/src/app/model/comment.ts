import { Post } from "./post";
import { User } from "./user";

export class Comment {
    id: number;
    content: string;
    timestamp: Date;
    userMadeBy: User;
    post: Post;
}