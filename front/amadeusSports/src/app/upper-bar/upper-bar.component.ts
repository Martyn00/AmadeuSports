import { Component, OnInit, ViewChild } from '@angular/core';
import { MatMenuTrigger } from '@angular/material/menu';
import { MatMenu } from '@angular/material/menu';
import { BetService } from '../service/bet.service';
import { PrincipalComponentLoaderService } from '../service/principal-component-loader.service';
@Component({
  selector: 'app-upper-bar',
  templateUrl: './upper-bar.component.html',
  styleUrls: ['./upper-bar.component.css']
})
export class UpperBarComponent implements OnInit {
  coins!: number;
  constructor(private loader: PrincipalComponentLoaderService, private betService: BetService) { }

  ngOnInit(): void {
    this.betService.coinsLoaded.subscribe((data) => {
      this.coins = data;
      console.log("this was called", this.coins);
    });
    this.betService.getCoins();

  }
  changeState(component: string) {
    this.loader.changeState(component);
  }
  isUserAdmin() {
    return (localStorage.getItem('role') === 'ADMIN');
  }
}
