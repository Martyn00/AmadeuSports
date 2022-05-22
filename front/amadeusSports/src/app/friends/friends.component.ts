import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { map, Observable, startWith } from 'rxjs';
import { UserDto } from '../dto/UserDto';
import { FriendService } from '../service/friend.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
  myControl = new FormControl();
  options!: UserDto[];
  filteredOptions!: Observable<UserDto[]>;
  displayedColumns = ['username', 'friend'];
  dataSource!: UserDto[];
  constructor(private friendService: FriendService, private userService: UserService) {
  }
  ngOnInit() {

    this.friendService.friendsLoaded
      .subscribe((data) => { this.dataSource = data; });
    this.friendService.populateFriendsTable();
    this.getUsers();

    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => (typeof value === 'string' ? value : value.user)),
      map(name => (name ? this._filter(name) : this.options.slice())),
    );
  }

  displayFn(user: UserDto): string {
    return user && user.username ? user.username : '';
  }

  private _filter(name: string): UserDto[] {
    const filterValue = name.toLowerCase();

    let users = this.options.filter(option => option.username.toLowerCase().includes(filterValue));
    // let slicedUsers = users.slice(0, 3);
    return users;
  }

  getUsers() {
    this.userService.getUsers().subscribe((data) => {
      this.options = data;
    });
    // this.options.forEach((elem) => console.log(elem));
  }
  submitData() {
    this.friendService.addFriend(this.myControl.value);
    this.friendService.populateFriendsTable();
    this.ngOnInit();
  }

  unfriend(element: UserDto) {
    this.friendService.deleteFriend(element.username);
    this.friendService.populateFriendsTable();
    this.ngOnInit();
  }
}

