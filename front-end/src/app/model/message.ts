import { Chat } from "./chat";
import { User } from "./user";

export class Message{
    id: number;
    content: String;
    timestamp: Date;
    chat: Chat;
    userMadeBy: User;
}