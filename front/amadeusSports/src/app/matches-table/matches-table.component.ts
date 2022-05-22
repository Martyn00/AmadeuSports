import { Component, Inject, Input, OnInit } from '@angular/core';
import { MatchDto } from '../dto/MatchDto';
import { LeaguesService } from '../service/leagues.service';
import { MatchTableLoaderService } from '../service/match-table-loader.service';
import { TeamsService } from '../service/teams.service';

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
  constructor(private matchTableService: MatchTableLoaderService, private teamService: TeamsService, private leagueService:LeaguesService) {
  }

  ngOnInit(): void {
    this.matchTableService.matchesLoaded
      .subscribe((data) => { console.log(data); this.dataSource = data; });

  }
  clickedFavorite(element: MatchDto) {
    element.isFavorite = !element.isFavorite;
    console.log(element);
    this.matchTableService.changeFavoriteState(element);
    this.ngOnInit();
  }

  favoritesLeague(element:MatchDto) {
    this.leagueService.changeFavoriteStateLeague(element.league);
    let updatedELement: MatchDto = element;
    updatedELement.league.isFavorite != element.league.isFavorite;
  }
  favoritesTeam1(element: MatchDto) {
    this.teamService.changeFavoriteStateTeam(element.team1, );
    let updatedELement: MatchDto = element;
    updatedELement.team1.isFavorite != element.team1.isFavorite;
  }
  favoritesTeam2(element: MatchDto) {
    this.teamService.changeFavoriteStateTeam(element.team2, );
    let updatedELement: MatchDto = element;
    updatedELement.team2.isFavorite != element.team2.isFavorite;
  }

}
