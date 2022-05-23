import { Component, OnInit } from '@angular/core';
import { TeamDto } from 'src/app/dto/TeamDto';
import { MatchTableLoaderService } from 'src/app/service/match-table-loader.service';
import { TeamsService } from 'src/app/service/teams.service';

@Component({
  selector: 'app-favorite-teams',
  templateUrl: './favorite-teams.component.html',
  styleUrls: ['./favorite-teams.component.css']
})
export class FavoriteTeamsComponent implements OnInit {
  teams!:TeamDto[];
  openedPanel!: number;
  constructor(private matchService:MatchTableLoaderService, private teamsService:TeamsService) { }
  openedId!: number;
  ngOnInit(): void {
    this.teamsService.teamsLoaded
      .subscribe((data) => { console.log(data); this.teams = data; });
    this.teamsService.getAllFavoriteTeams();
  }

  openGroup(team: TeamDto) {

    console.log(team.id);
    this.matchService.populatMatchtableWithFavorites("teams", team.id);
  }
  removeFromFavorites(element: TeamDto) {
    this.teamsService.changeFavoriteStateTeam(element);
    this.ngOnInit();
  }
}
