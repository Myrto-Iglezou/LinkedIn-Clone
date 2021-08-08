export class UserDetails {
  id: number;
  token: string;
  roles: Array<string> = new Array<string>();
  accepted: boolean;

  hasRole(rolename: string): boolean {
    let flag = false;
    this.roles.forEach((role) => {
      if (role === rolename) flag = true;
    });
    return flag;
  }
}
