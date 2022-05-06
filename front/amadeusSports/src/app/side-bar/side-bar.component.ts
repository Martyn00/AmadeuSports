import { Component, OnInit } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {
  typeOfLog: string = 'log-in';
  logBool: boolean = true; 
  isSignedIn: boolean = false;
  ngOnInit(): void {
  }
  changeState(): void {
    if (this.typeOfLog === 'log-in') {
        this.typeOfLog = 'register'
    } else {
      this.typeOfLog = "log-in"
    }
  }
}
