import { User } from "./user";

export class Job {
    id:number;
    title: string;
    description:string;
    timestamp: Date;
    userMadeBy: User;
    usersApplied: Array<User> = new Array<User>();
}