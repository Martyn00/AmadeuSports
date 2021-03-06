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
  options: string[] = [];
  filteredOptions!: Observable<string[]>;
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
      map(value => this._filter(value)),
    );
  }

  private _filter(value: string): string[] {
    const filterValue = this._normalizeValue(value);
    return this.options.filter(option => this._normalizeValue(option).includes(filterValue));
  }

  private _normalizeValue(value: string): string {
    return value.toLowerCase().replace(/\s/g, '');
  }

  getUsers() {
    this.userService.getUsers().subscribe((data) => {
      this.options = data.map(user => user.username);
    });
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

