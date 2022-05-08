import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewContainerRef } from '@angular/core';
import { MatchesTableComponent } from '../matches-table/matches-table.component';
import { MatchTableLoaderService } from '../service/match-table-loader.service';
@Component({
  selector: 'app-match-tabs',
  templateUrl: './match-tabs.component.html',
  styleUrls: ['./match-tabs.component.css']
})
export class MatchTabsComponent implements OnInit {

  constructor(private matchTableLoaderService:MatchTableLoaderService) { }


  ngOnInit(): void {
    this.matchTableLoaderService.populateMatchTable(0);
  }

  tabChange(index: number) {
    this.matchTableLoaderService.populateMatchTable(index);
  }
}
