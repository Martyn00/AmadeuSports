import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {
  firstNameAutofilled: boolean;
  lastNameAutofilled: boolean;

  username = new FormControl('');
  password = new FormControl('');

  constructor(private userService: UserService, private _snackBar: MatSnackBar) {
    this.firstNameAutofilled = false;
    this.lastNameAutofilled = false;
  }

  ngOnInit(): void {

  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action);
  }
  submitData() {
    this.userService.logIn(this.username.value, this.password.value);
  }

}
