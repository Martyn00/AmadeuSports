import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y/input-modality/input-modality-detector';
import { formatDate } from '@angular/common';
import { Component, Inject, Input, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BetDialogComponent } from '../bet/bet-dialog/bet-dialog.component';
import { MatchDto } from '../dto/MatchDto';
import { ResultDto } from '../dto/ResultDto';
import { BetService } from '../service/bet.service';
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

  @ViewChild('dialogRef')
  dialogRef!: TemplateRef<any>;

  constructor(private matchTableService: MatchTableLoaderService, private teamService: TeamsService,
    private leagueService: LeaguesService, public dialog: MatDialog, private _snackBar: MatSnackBar, private betService: BetService) {
  }

  ngOnInit(): void {
    this.matchTableService.matchesLoaded
      .subscribe((data) => { console.log(data); this.dataSource = data; });

  }
  clickedFavorite(element: MatchDto) {
    element.isFavorite = !element.isFavorite;
    this.matchTableService.changeFavoriteState(element);
  }

  favoritesLeague(element: MatchDto) {
    this.leagueService.changeFavoriteStateLeague(element.league);
    let updatedELement: MatchDto = element;
    updatedELement.league.isFavorite != element.league.isFavorite;
  }
  favoritesTeam1(element: MatchDto) {
    this.teamService.changeFavoriteStateTeam(element.team1,);
    let updatedELement: MatchDto = element;
    updatedELement.team1.isFavorite != element.team1.isFavorite;
  }
  favoritesTeam2(element: MatchDto) {
    this.teamService.changeFavoriteStateTeam(element.team2);
    let updatedELement: MatchDto = element;
    updatedELement.team2.isFavorite != element.team2.isFavorite;
  }
  clickedBet(element: MatchDto) {

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
