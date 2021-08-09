import { Image } from './image';
import { Role } from './role';

export class User {
  id: number;
  name: string;
  surname: string;
  username: string;
  password: string;
  passwordConfirm: string;
  role: string;
  phoneNumber: string;
  imageFile: Image;
  accepted: boolean;
}
