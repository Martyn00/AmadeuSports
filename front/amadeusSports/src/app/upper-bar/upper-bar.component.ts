import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatMenuTrigger } from '@angular/material/menu';
import { MatMenu } from '@angular/material/menu';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { SearchDto } from '../dto/SearchDto';
import { BetService } from '../service/bet.service';
import { PrincipalComponentLoaderService } from '../service/principal-component-loader.service';
import { SearchService } from '../service/search.service';
@Component({
  selector: 'app-upper-bar',
  templateUrl: './upper-bar.component.html',
  styleUrls: ['./upper-bar.component.css']
})
export class UpperBarComponent implements OnInit {
  coins!: number;
  constructor(private loader: PrincipalComponentLoaderService, private betService: BetService, private searchService:SearchService) { }
  slideValue!: string;
  group = new FormGroup({
    name: new FormControl('')
  })
  
  ngOnInit(): void {
    this.betService.coinsLoaded.subscribe((data) => {
      this.coins = data;
      console.log("this was called", this.coins);
    });
    this.betService.getCoins();
    this.slideValue = "teams"
  }
  changeState(component: string) {
    this.loader.changeState(component);
  }
  isUserAdmin() {
    return (localStorage.getItem('role') === 'ADMIN');
  }
  isUserLogged() {
    return (localStorage.getItem('token') !== null);
  }
  onChange($event: MatSlideToggleChange) {
    if (this.slideValue === "teams") {
      this.slideValue = "league"
    } else {
      this.slideValue = "teams"
    }
  }
  onEnter() {
    const { name } = this.group.value;
    let searchDto: SearchDto = new SearchDto(name, this.slideValue);
    this.searchService.showDataForSearch(searchDto);
    this.changeState('/result')
  }
}
