import { User } from "./user";

export class Comment {
    id: number;
    content: string;
    date: Date;
    userMadeBy: User;
}