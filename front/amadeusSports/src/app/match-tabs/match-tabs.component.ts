import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewContainerRef } from '@angular/core';
import { MatchesTableComponent } from '../matches-table/matches-table.component';
import { MatchTableLoaderService } from '../service/match-table-loader.service';
import { TeamsService } from '../service/teams.service';
@Component({
  selector: 'app-match-tabs',
  templateUrl: './match-tabs.component.html',
  styleUrls: ['./match-tabs.component.css']
})
export class MatchTabsComponent implements OnInit {

  constructor(private matchTableLoaderService: MatchTableLoaderService, private teamService: TeamsService) { }
  todayDate: Date = new Date();
  pDate: Date = new Date();
  ppDate: Date = new Date();
  fDate: Date = new Date();
  ffDate: Date = new Date();
  currentPosition!: number;
  ngOnInit(): void {
    this.matchTableLoaderService.populateMatchTable(-3);
    this.currentPosition = 0;
    this.pDate.setDate(this.todayDate.getDate() - 2);
    this.ppDate.setDate(this.todayDate.getDate() - 3);
    this.fDate.setDate(this.todayDate.getDate() + 2);
    this.ffDate.setDate(this.todayDate.getDate() + 3);
    this.teamService.favoriteChanged.subscribe(data => {
      this.matchTableLoaderService.populateMatchTable(this.currentPosition - 3);
      console.log("intr");
    });
}

tabChange(index: number) {
  this.currentPosition = index;
  console.log(index - 3);
  this.matchTableLoaderService.populateMatchTable(index - 3);
}
}
