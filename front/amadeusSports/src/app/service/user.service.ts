import { Injectable } from '@angular/core';
import { UserDto } from '../dto/UserDto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginResponseDto } from '../dto/LoginResponseDto';

const URL = "http://localhost:8080/AmadeusSports"

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};

@Injectable({
    providedIn: 'root'
})
export class UserService {
    constructor(private http: HttpClient) { }
    son!: string;
    loginResponseDto!: LoginResponseDto;

    register(userDto: UserDto) {
        userDto.role = 'USER';
        this.http.post<any>(URL + '/register', userDto, httpOptions).subscribe(data => {
            console.log(data);
        })
    }

    logIn(username: string, password: string) {
        console.log("intra in log in")
        this.son = JSON.stringify({ username, password });
        console.log(this.son);
        this.http.post<any>(URL + '/login', this.son, httpOptions).subscribe(response => {
            localStorage.setItem("token", response.jwt);
        })
    }
}
