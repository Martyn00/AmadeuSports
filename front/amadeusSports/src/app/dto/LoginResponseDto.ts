
export class LoginResponseDto {
    jwt: string = '';
    role: string = '';
    username: string = '';
    constructor(jwt:string, role:string, username:string) {
        this.jwt = jwt;
        this.role = role;
        this.username = username;
    }
}