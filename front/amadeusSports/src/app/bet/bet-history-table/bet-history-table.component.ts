import { Component, OnInit } from '@angular/core';
import { BetDto } from 'src/app/dto/BetDto';
import { BetService } from 'src/app/service/bet.service';
import { LeaguesService } from 'src/app/service/leagues.service';
import { TeamsService } from 'src/app/service/teams.service';

@Component({
  selector: 'app-bet-history-table',
  templateUrl: './bet-history-table.component.html',
  styleUrls: ['./bet-history-table.component.css']
})
export class BetHistoryTableComponent implements OnInit {

  constructor(private betService: BetService,private teamService: TeamsService,private leagueService: LeaguesService) { }
  dataSource!: BetDto[];
  displayedColumns: string[] = ['time', 'team1', 'team2', 'result', 'league', 'status', 'coins'];
  ngOnInit(): void {
    this.betService.betsHistoryLoaded.subscribe(data => {
      this.dataSource = data;
      console.log(this.dataSource);
    })
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
}
