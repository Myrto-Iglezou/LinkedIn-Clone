import { User } from "./user";
import { Message } from "./message";

export class Chat{
    id: number;
    timestamp: Date;
    users: Array<User> = new Array<User>();
    messages: Array<Message> = new Array<Message>();
    latestMessage: Message;
}