
export class UserDto {
    id?: number;
    username: string = '';
    password?: string = '';
    email?: string = '';
    role?: string = '';
    constructor(id:number, username:string, password:string, email:string, role:string) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
    }

}