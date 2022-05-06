import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
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

  constructor(private userService:UserService) {
    this.firstNameAutofilled = false;
    this.lastNameAutofilled = false;
  }

  ngOnInit(): void {

  }

  submitData() {
    this.userService.logIn(this.username.value, this.password.value)
  }

}
