import { Component, OnInit, ViewChild } from '@angular/core';
import { MatMenuTrigger } from '@angular/material/menu';
import { MatMenu } from '@angular/material/menu';
import { PrincipalComponentLoaderService } from '../service/principal-component-loader.service';
@Component({
  selector: 'app-upper-bar',
  templateUrl: './upper-bar.component.html',
  styleUrls: ['./upper-bar.component.css']
})
export class UpperBarComponent implements OnInit {

  constructor(private loader:PrincipalComponentLoaderService) { }

  ngOnInit(): void {
  }
  changeState(component:string) {
    this.loader.changeState(component);
  }
}
