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
    this.http.get<UserDto[]>(url).subscribe(response => {
      this.sendUsers = response;
      console.log("friends " + this.sendUsers);
      this.friendsLoaded.emit(this.sendUsers);
    });
  }
  addFriend(username: string) {
    console.log("aici" + username);
    let url = URL + '/user/' + username + '/add-friend-by-username'
    this.http.post<any>(url, null, httpOptions).subscribe(response => {
    });
    this.populateFriendsTable();
  }
  deleteFriend(username: string) {
    let url = URL + '/user/' + username + '/remove-friend-by-username'
    this.http.post<any>(url, null, httpOptions).subscribe(response => {
    });
    this.populateFriendsTable();
  }
}
