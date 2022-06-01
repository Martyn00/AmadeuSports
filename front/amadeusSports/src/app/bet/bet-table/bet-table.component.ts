import { Component, OnInit } from '@angular/core';
import { BetDto } from 'src/app/dto/BetDto';
import { UserBetChoiceDto } from 'src/app/dto/UserBetChoiceDto';
import { UserDto } from 'src/app/dto/UserDto';
import { BetService } from 'src/app/service/bet.service';
import { LeaguesService } from 'src/app/service/leagues.service';
import { TeamsService } from 'src/app/service/teams.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-bet-table',
  templateUrl: './bet-table.component.html',
  styleUrls: ['./bet-table.component.css']
})
export class BetTableComponent implements OnInit {

  constructor(private betService: BetService, private userService: UserService,
    private teamService: TeamsService, private leagueService: LeaguesService) { }
  dataSource!: BetDto[];
  displayedColumns: string[] = ['time', 'team1', 'team2', 'result', 'league', 'bet', 'status', 'coins'];
  user!: UserDto;
  ngOnInit(): void {
    this.betService.betsCurrentLoaded.subscribe(data => {
      this.dataSource = data;
      console.log(this.dataSource);
    })
    this.userService.getLoggedInUser().subscribe(data => {
      this.user = data;
    });
  }

  favoritesTeam1(element: BetDto) {
    this.teamService.getTeamByName(element.team1).subscribe((team) => {
      this.teamService.changeFavoriteStateTeam(team);
    });
  }

  favoritesTeam2(element: BetDto) {
    this.teamService.getTeamByName(element.team2).subscribe((team) => {
      this.teamService.changeFavoriteStateTeam(team);
    });
  }
  
  favoritesLeague(element: BetDto) {
    this.leagueService.getleagueByName(element.league).subscribe((league) => {
      this.leagueService.changeFavoriteStateLeague(league);
    });

  }

  getLoggedUserBet(element: BetDto) {

    if (this.user.id === element.betChoice.user1Id) {
      return element.betChoice.user1Choice
    }
    if (this.user.id === element.betChoice.user2Id) {
      return element.betChoice.user2Choice
    }
    return -1;
  }
}
