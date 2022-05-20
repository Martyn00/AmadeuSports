import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewContainerRef } from '@angular/core';
import { MatchesTableComponent } from '../matches-table/matches-table.component';
import { MatchTableLoaderService } from '../service/match-table-loader.service';
@Component({
  selector: 'app-match-tabs',
  templateUrl: './match-tabs.component.html',
  styleUrls: ['./match-tabs.component.css']
})
export class MatchTabsComponent implements OnInit {

  constructor(private matchTableLoaderService: MatchTableLoaderService) { }
  todayDate: Date = new Date();
  pDate: Date = new Date();
  ppDate: Date = new Date();
  fDate: Date = new Date();
  ffDate: Date = new Date();
  ngOnInit(): void {
    this.matchTableLoaderService.populateMatchTable(0);
    this.pDate.setDate(this.todayDate.getDate() - 2);
    this.ppDate.setDate(this.todayDate.getDate() - 3);
    this.fDate.setDate(this.todayDate.getDate() + 2);
    this.ffDate.setDate(this.todayDate.getDate() + 3);

  }

  tabChange(index: number) {

    console.log(index - 3);
    this.matchTableLoaderService.populateMatchTable(index - 3);
  }
}
