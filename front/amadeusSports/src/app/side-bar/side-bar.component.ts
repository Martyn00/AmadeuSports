import { Component, OnInit } from '@angular/core';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { PrincipalComponentLoaderService } from '../service/principal-component-loader.service';

const LOG_IN_STR = 'log-in';
const REG_STR = 'register'
@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {
  component: string = LOG_IN_STR;
  isSignedIn: boolean = false;
  constructor(private route: Router, private loader: PrincipalComponentLoaderService) { }
  ngOnInit(): void {
    this.loader.stateLoaded
      .subscribe((data) => { this.changeState(data); });
    if (localStorage.getItem('token') !== null) {
      this.isSignedIn = true;
    }
  }

  changeState(component: string): void {
    this.component = component;
    this.route.navigate([component]);
  }
}
