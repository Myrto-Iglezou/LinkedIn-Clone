import { User } from "./user";

export class SkillsAndExperience{
    id: number;
    type: string;
    description: string;
    isPublic: boolean;
    user: User;
}