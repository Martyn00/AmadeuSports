import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { even } from '@rxweb/reactive-form-validators';
import { BetService } from 'src/app/service/bet.service';

const map = new Map<number, string>([
  [0, "history"],
  [1, "current"],
  [2, "pending"]
]);
@Component({
  selector: 'app-bets',
  templateUrl: './bets.component.html',
  styleUrls: ['./bets.component.css']
})
export class BetsComponent implements OnInit {

  constructor(private betService: BetService) { }

  ngOnInit(): void {
  }
  tabChange(index: number) {
    this.betService.getUserBets(map.get(index)!);
  }

}
