import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatchDto } from '../dto/MatchDto';
import { MatchTableLoaderService } from '../service/match-table-loader.service';


@Component({
  selector: 'app-matches-table',
  templateUrl: './matches-table.component.html',
  styleUrls: ['./matches-table.component.css']
})
export class MatchesTableComponent implements OnInit {
  displayedColumns: string[] = ['time', 'team1', 'team2', 'score', 'league', 'sport', 'details', 'actions', 'favorites'];
  dataSource!: MatchDto[];
  isChecked!: boolean;
  favoriteText!: string;
  constructor(private matchTableService: MatchTableLoaderService) {
  }

  ngOnInit(): void {
    this.matchTableService.matchesLoaded
      .subscribe((data) => { console.log(data); this.dataSource = data; });
    this.matchTableService.populateMatchTable(0);

  }
  clickedFavorite(element: MatchDto) {
    element.isFavorite = !element.isFavorite;
  }
}
