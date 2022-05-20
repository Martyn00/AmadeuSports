import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { UserDto } from '../dto/UserDto';

const URL = "http://localhost:8080/AmadeusSports"
const httpOptions = {
  headers: new HttpHeaders(
    {
    'Content-Type': 'application/json',
    }
  )
};

@Injectable({
  providedIn: 'root'
})
export class FriendService {
  public friendsLoaded = new EventEmitter<UserDto[]>();
  sendUsers: UserDto[] = [];

  constructor(private http: HttpClient) { }

  populateFriendsTable() {
    let url = URL + '/user/friends';
    console.log(url);
    console.log("token=" + localStorage.getItem("token"));
    this.http.get<UserDto[]>(url).subscribe(response => {
      this.sendUsers = response;
      console.log("friends " + this.sendUsers);
      this.friendsLoaded.emit(this.sendUsers);
    });
  }
  addFriend(username: string) {
    let url = URL + 'user/' + username + '/add-friend'
    this.http.post<any>(url, null, httpOptions).subscribe(response => {
    });
  }
}
