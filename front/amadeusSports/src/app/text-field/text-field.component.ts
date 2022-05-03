import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-text-field',
  templateUrl: './text-field.component.html',
  styleUrls: ['./text-field.component.css']
})
export class TextFieldComponent implements OnInit {
  firstNameAutofilled: boolean;
  lastNameAutofilled: boolean;
  

  constructor() { 
    this.firstNameAutofilled = false;
    this.lastNameAutofilled = false;
  }
   
  ngOnInit(): void {
    
  }
  getData(username: string, password: string) {
    console.log(username);
    console.log(password);
  }
}
