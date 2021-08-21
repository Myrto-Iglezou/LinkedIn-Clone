import { User } from "./user";

export class Connection {
    id: number;
    isAccepted: boolean;
    userFollowing: User;
    userFollowed: User;
}