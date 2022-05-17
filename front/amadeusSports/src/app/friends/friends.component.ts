import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { map, Observable, startWith } from 'rxjs';
import { UserDto } from '../dto/UserDto';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {
  myControl = new FormControl();
  options: UserDto[] = [{ username: 'Mary', password: '', email: '', role: 'user' }, { username: 'Shelley', password: '', email: '', role: 'user' }, { username: 'Igor', password: '', email: '', role: 'user' }];
  filteredOptions!: Observable<UserDto[]>;

  ngOnInit() {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => (typeof value === 'string' ? value : value.name)),
      map(name => (name ? this._filter(name) : this.options.slice())),
    );
  }

  displayFn(user: UserDto): string {
    return user && user.username ? user.username : '';
  }

  private _filter(name: string): UserDto[] {
    const filterValue = name.toLowerCase();

    let users = this.options.filter(option => option.username.toLowerCase().includes(filterValue));
    let slicedUsers = users.slice(0, 3);
    return slicedUsers;
  }
}

