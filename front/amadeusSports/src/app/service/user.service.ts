import { EventEmitter, Injectable } from '@angular/core';
import { UserDto } from '../dto/UserDto';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginResponseDto } from '../dto/LoginResponseDto';
import { PrincipalComponentLoaderService } from './principal-component-loader.service';
import { Observable } from 'rxjs';
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
    constructor(private http: HttpClient, private loader:PrincipalComponentLoaderService) { }
    son!: string;
    loginResponseDto!: LoginResponseDto;
    public usersLoaded = new EventEmitter<UserDto[]>();
    sendUsers: UserDto[] = [];
    
    register(userDto: UserDto) {
        userDto.role = 'USER';
        this.http.post<any>(URL + '/register', userDto, httpOptions).subscribe(data => {
            console.log(data);
        })
        this.loader.changeState("/match-tabs")
    }

    logIn(username: string, password: string) {
        this.son = JSON.stringify({ username, password });
        this.http.post<any>(URL + '/login', this.son, httpOptions).subscribe(response => {
            localStorage.setItem("token", response.jwt);
            console.log(response.jwt);
        });
        this.loader.changeState("/match-tabs")
    }
    getUsers() {
        return this.http.get<UserDto[]>(URL + '/user/all')
    }
}
