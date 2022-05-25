import { Component, OnInit } from '@angular/core';
import { BetDto } from 'src/app/dto/BetDto';
import { UserDto } from 'src/app/dto/UserDto';
import { BetService } from 'src/app/service/bet.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-upcoming-bet-table',
  templateUrl: './upcoming-bet-table.component.html',
  styleUrls: ['./upcoming-bet-table.component.css']
})
export class UpcomingBetTableComponent implements OnInit {

  constructor(private betService: BetService, private userService: UserService) { }
  dataSource!: BetDto[];
  displayedColumns: string[] = ['time', 'team1', 'team2', 'league', 'status', 'coins', 'button'];
  user!: UserDto;
  ngOnInit(): void {
    this.betService.betsPendingLoaded.subscribe(data => {
      this.dataSource = data;
      console.log(this.dataSource);
    })
    this.userService.getLoggedInUser().subscribe(data => {
      this.user = data;
      console.log(this.user);
    })
  }

  favoritesTeam1(element: BetDto) {

  }

  favoritesTeam2(element: BetDto) {

  }
  favoritesLeague(element: BetDto) {

  }
  
  getUsedBet(element: BetDto) {
    if (this.user.id === element.betChoice.user1Id) {
      return element.betChoice.user1Id
    }
    if (this.user.id === element.betChoice.user2Id) {
      return element.betChoice.user2Id
    }
    return -1;
  }
  hasLoggedUserBet(element: BetDto) {
    if (this.user.id == element.betChoice.user1Id) {
      if (element.betChoice.user1Choice === -1) {
        return false;
      }
    }
    console.log(element.betChoice);
    if (this.user.id == element.betChoice.user2Id) {
      if (element.betChoice.user2Choice !== -1) {
        return false;
      }
    }
    return true;
  }
}
