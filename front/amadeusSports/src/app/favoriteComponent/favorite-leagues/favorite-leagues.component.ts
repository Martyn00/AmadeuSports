import { Component, OnInit } from '@angular/core';
import { LeagueDto } from 'src/app/dto/LeagueDto';
import { LeaguesService } from 'src/app/service/leagues.service';
import { MatchTableLoaderService } from 'src/app/service/match-table-loader.service';

@Component({
  selector: 'app-favorite-leagues',
  templateUrl: './favorite-leagues.component.html',
  styleUrls: ['./favorite-leagues.component.css']
})
export class FavoriteLeaguesComponent implements OnInit {
  panelOpenState = false;
  leagues !: LeagueDto[];
  constructor(private matchService: MatchTableLoaderService, private leagueService: LeaguesService) { }

  ngOnInit(): void {
    this.leagueService.leaguesLoaded.subscribe((data) => { console.log(data); this.leagues = data; });
    this.leagueService.getAllFavoriteLeagues()
  }
  openGroup(league: LeagueDto) {
    console.log(league.id);
    this.matchService.populatMatchtableWithFavorites("leagues", league.id);
  }
  removeFromFavorites(element: LeagueDto) {
    this.leagueService.changeFavoriteStateLeague(element);
    this.leagueService.getAllFavoriteLeagues()
    this.removeElementFromLeagues(element);
  }
  removeElementFromLeagues(element: LeagueDto) {
    const index = this.leagues.indexOf(element, 0);
    if (index > -1) {
      this.leagues.splice(index, 1);
    }
  }
}
