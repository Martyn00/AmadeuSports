import { Component, OnInit } from '@angular/core';
import { BetDto } from 'src/app/dto/BetDto';
import { BetService } from 'src/app/service/bet.service';

@Component({
  selector: 'app-upcoming-bet-table',
  templateUrl: './upcoming-bet-table.component.html',
  styleUrls: ['./upcoming-bet-table.component.css']
})
export class UpcomingBetTableComponent implements OnInit {

  constructor(private betService: BetService) { }
  dataSource!: BetDto[];
  displayedColumns: string[] = ['time', 'team1', 'team2', 'result', 'league', 'status', 'coins'];
  ngOnInit(): void {
    this.betService.betsPendingLoaded.subscribe(data => {
      this.dataSource = data;
      console.log(this.dataSource);
    })
  }

  favoritesTeam1(element: BetDto) {

  }

  favoritesTeam2(element: BetDto) {

  }
  favoritesLeague(element: BetDto) {

  }

}
