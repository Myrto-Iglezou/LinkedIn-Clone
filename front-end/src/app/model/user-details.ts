export class UserDetails {
  id: number;
  token: string;
  role: string;
  accepted: boolean;

  hasRole(rolename: string): boolean {
    let flag = false;

    if (this.role === rolename) flag = true;

    return flag;
  }
}
