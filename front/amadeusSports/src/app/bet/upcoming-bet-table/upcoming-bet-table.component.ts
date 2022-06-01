import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { BetDto } from 'src/app/dto/BetDto';
import { TeamDto } from 'src/app/dto/TeamDto';
import { UserDto } from 'src/app/dto/UserDto';
import { BetService } from 'src/app/service/bet.service';
import { LeaguesService } from 'src/app/service/leagues.service';
import { TeamsService } from 'src/app/service/teams.service';
import { UserService } from 'src/app/service/user.service';
import { AcceptBetDialogComponent } from '../accept-bet-dialog/accept-bet-dialog.component';

@Component({
  selector: 'app-upcoming-bet-table',
  templateUrl: './upcoming-bet-table.component.html',
  styleUrls: ['./upcoming-bet-table.component.css']
})
export class UpcomingBetTableComponent implements OnInit {

  constructor(private betService: BetService, private userService: UserService,
    public dialog: MatDialog, private teamService: TeamsService,
    private leagueService: LeaguesService) { }
  dataSource!: BetDto[];
  displayedColumns: string[] = ['time', 'team1', 'team2', 'league', 'status', 'coins', 'button'];
  user!: UserDto;
  ngOnInit(): void {
    this.betService.betsPendingLoaded.subscribe(data => {
      this.dataSource = data;
    })
    this.userService.getLoggedInUser().subscribe(data => {
      this.user = data;
    })
  }

  favoritesTeam1(element: BetDto) {
    this.teamService.getTeamByName(element.team1).subscribe((team) => {
      this.teamService.changeFavoriteStateTeam(team);
    });;
  }

  favoritesTeam2(element: BetDto) {
    this.teamService.getTeamByName(element.team2).subscribe((team) => {
      this.teamService.changeFavoriteStateTeam(team);
    });;


  }
  favoritesLeague(element: BetDto) {
    this.leagueService.getleagueByName(element.league).subscribe((league) => {
      this.leagueService.changeFavoriteStateLeague(league);
    });
  
  }
  
  getUserWhoAlreadybet(element: BetDto) {
    if (this.user.id === element.betChoice.user1Id) {
      return element.betChoice.user1Id;
    }
    if (this.user.id === element.betChoice.user2Id) {
      return element.betChoice.user2Id;
    }
    return -1;
  }

  hasLoggedUserBet(element: BetDto) {
    if (this.user.id == element.betChoice.user1Id) {
      if (element.betChoice.user1Choice === -1) {
        return false;
      }
    }
    if (this.user.id == element.betChoice.user2Id) {
      if (element.betChoice.user2Choice !== -1) {
        return false;
      }
    }
    return true;
  }
  cancel(element: BetDto) {
    this.betService.cancelBet(element);
    this.betService.getUserBets('pending');
  }

  accept(bet: BetDto) {
    let userId = this.getUserWhoAlreadybet(bet);
    const myTempDialog = this.dialog.open(AcceptBetDialogComponent, { data: [bet, userId]});
    myTempDialog.afterClosed().subscribe((res) => {
      let result = res;
      if (result == -1) {
        this.cancel(bet);
        return;
      }
      this.betService.acceptBet(result, bet);

    });
    this.betService.getUserBets('pending');
  }
}
