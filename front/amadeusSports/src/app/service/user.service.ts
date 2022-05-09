import { Injectable } from '@angular/core';
import { UserDto } from '../dto/UserDto';
import { HttpClient } from '@angular/common/http';

const URL = "http://localhost:8080/AmadeusSports/register"


@Injectable({
    providedIn: 'root'
})
export class UserService {
    constructor(private http: HttpClient) { }

    register(userDto: UserDto) {
        userDto.role = 'USER';
        this.http.post<any>(URL, userDto).subscribe(data => {
            console.log(data);
        })
    }
    logIn(username: string, password: string) {
         console.log("intra in log in")
        this.http.post<any>(URL, JSON.stringify([username, password])).subscribe(data => {
            console.log(data);
        })
    }
}
