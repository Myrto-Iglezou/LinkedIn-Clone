import { User } from "./user";

export class Connection {
    id: number;
    isAccepted: boolean;
    // timestamp: Date;
    userFollowing: User;
    userFollowed: User;
}