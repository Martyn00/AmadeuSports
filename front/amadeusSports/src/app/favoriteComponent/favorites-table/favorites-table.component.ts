import { Component, OnInit } from '@angular/core';
import { MatchDto } from 'src/app/dto/MatchDto';
import { LeaguesService } from 'src/app/service/leagues.service';
import { MatchTableLoaderService } from 'src/app/service/match-table-loader.service';
import { TeamsService } from 'src/app/service/teams.service';

@Component({
  selector: 'app-favorites-table',
  templateUrl: './favorites-table.component.html',
  styleUrls: ['./favorites-table.component.css']
})
export class FavoritesTableComponent implements OnInit {
  displayedColumns: string[] = ['time', 'team1', 'team2', 'score', 'league', 'sport', 'details', 'actions', 'favorites'];
  dataSource!: MatchDto[];
  isChecked!: boolean;
  favoriteText!: string;
  constructor(private matchTableService: MatchTableLoaderService, private teamService: TeamsService, private leagueService: LeaguesService) {
  }

  ngOnInit(): void {
    this.matchTableService.favoriteMatchesLoaded
      .subscribe((data) => { console.log(data); this.dataSource = data; });

  }
  clickedFavorite(element: MatchDto) {
    element.isFavorite = !element.isFavorite;
    console.log(element);
    this.matchTableService.changeFavoriteState(element);
    this.matchTableService.populatMatchtableWithFavorites("matches", 0);
    this.ngOnInit();
  }

  favoritesLeague(element: MatchDto) {
    this.leagueService.changeFavoriteStateLeague(element.league);
    let updatedELement: MatchDto = element;
    updatedELement.league.isFavorite != element.league.isFavorite;
  }
  favoritesTeam1(element: MatchDto) {
    this.teamService.changeFavoriteStateTeam(element.team1);
    let updatedELement: MatchDto = element;
    updatedELement.team1.isFavorite != element.team1.isFavorite;
  }
  favoritesTeam2(element: MatchDto) {
    this.teamService.changeFavoriteStateTeam(element.team2);
    let updatedELement: MatchDto = element;
    updatedELement.team2.isFavorite != element.team2.isFavorite;
  }
}
