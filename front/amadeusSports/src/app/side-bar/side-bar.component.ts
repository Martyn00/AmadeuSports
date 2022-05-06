import { Component, OnInit } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';


const LOG_IN_STR = 'log-in';
const REG_STR ='register'
@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {
  component: string = LOG_IN_STR;
  logBool: boolean = true; 
  isSignedIn: boolean = false;
  showLogIn: boolean = true;
  showRegister: boolean = false;
  ngOnInit(): void {
  }

  changeState(component: string): void {
    this.component = component;
    if (component === 'log-in') {
      this.showLogIn = true;
      this.showRegister = false;
    } else if (component === 'register') {
      this.showLogIn = false;
      this.showRegister = true;
    }
  }
}
