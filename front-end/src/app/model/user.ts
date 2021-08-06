import {Image} from './image';

export class User {
  id: number;
  firstName: string;
  lastName: string;
  password_1: string;
  password_2: string;
  email: string;
  phone: string;
  profilePhoto: Image;
  accepted: boolean;
}
