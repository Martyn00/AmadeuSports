import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BetDialogComponent } from 'src/app/bet/bet-dialog/bet-dialog.component';
import { MatchDto } from 'src/app/dto/MatchDto';
import { ResultDto } from 'src/app/dto/ResultDto';
import { BetService } from 'src/app/service/bet.service';
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
  constructor(private matchTableService: MatchTableLoaderService, private teamService: TeamsService, private leagueService: LeaguesService,
    public _snackBar: MatSnackBar, public dialog: MatDialog, private betService:BetService) {
  }

  ngOnInit(): void {
    this.matchTableService.favoriteMatchesLoaded
      .subscribe((data) => { console.log(data); this.dataSource = data; });

  }
  clickedFavorite(element: MatchDto) {
    element.isFavorite = !element.isFavorite;
    console.log(element);
    this.matchTableService.changeFavoriteState(element);
    // this.matchTableService.populatMatchtableWithFavorites("matches", 0);
    this.removeElementFromMatches(element);
  }
  removeElementFromMatches(element: MatchDto) {
    const index = this.dataSource.indexOf(element, 0);
    if (index > -1) {
      this.dataSource.splice(index, 1);
    }
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
  bet(element: MatchDto) {
    let matchDate = formatDate(element.time, 'yyyy-MM-ddThh:mm;ss', 'en_US');
    let now = formatDate(new Date(), 'yyyy-MM-ddThh:mm;ss', 'en_US');
    if (matchDate < now) {
      this._snackBar.open("You cannot bet on this match!", "Dismiss");
      return;
    }

    const myTempDialog = this.dialog.open(BetDialogComponent, { data: element.id });
    myTempDialog.afterClosed().subscribe((res) => {
      let result: ResultDto = res;
      console.log(result);
      if (result.betType === 'PENDING' || result.amount === -1 || result.username === '') {
        this._snackBar.open("You did not fill all the necessary fields", "Dismiss");
        return;
      }
      // Data back from dialog
      this.betService.bet(result);
      this.betService.getCoins()
      console.log("aici da");
    });
  }
}
