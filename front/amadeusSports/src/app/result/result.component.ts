import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LeagueDto } from '../dto/LeagueDto';
import { MatchDto } from '../dto/MatchDto';
import { TeamDto } from '../dto/TeamDto';
import { LeaguesService } from '../service/leagues.service';
import { SearchService } from '../service/search.service';
import { TeamsService } from '../service/teams.service';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {

  constructor(private searchService: SearchService, private teamService: TeamsService,private leagueService:LeaguesService, public dialog: MatDialog, private _snackBar: MatSnackBar) { }
  historyMatches!: MatchDto[];
  upcomingMatches!: MatchDto[];
  league!: LeagueDto;
  team!: TeamDto;
  isLeague!: boolean;
  ngOnInit(): void {
    this.searchService.matchHistoryLoaded.subscribe(data => {
      this.historyMatches = data;
    });
    this.searchService.matchUpcomingLoaded.subscribe(data => {
      this.upcomingMatches = data;
    });
    this.searchService.leagueLoaded.subscribe(data => {
      this.league = data;
      this.isLeague = true;
    });
    this.searchService.teamLoaded.subscribe(data => {
      this.team = data;
      this.isLeague = false;
    });
  }
  clickedFavoriteTeam(team: TeamDto) {
    // if (team.isFavorite) {
    //   this._snackBar.open("Team is already favorite!", "Dismiss");
    // }
    this.teamService.changeFavoriteStateTeam(team);
    this.searchService.showDataForSearch(this.searchService.searchDto);
    
  }
  clickedFavoriteLeague(league: LeagueDto) {
    // if (league.isFavorite) {
    //   this._snackBar.open("League is already favorite!", "Dismiss");
    // }
    this.leagueService.changeFavoriteStateLeague(league);
    league.isFavorite != league.isFavorite;
    this.searchService.showDataForSearch(this.searchService.searchDto);
  }
}
