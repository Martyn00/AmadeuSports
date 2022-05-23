import { Component, OnInit } from '@angular/core';
import { MatchesTableComponent } from 'src/app/matches-table/matches-table.component';
import { MatchTableLoaderService } from 'src/app/service/match-table-loader.service';

@Component({
  selector: 'app-favorite-matches',
  templateUrl: './favorite-matches.component.html',
  styleUrls: ['./favorite-matches.component.css']
})
export class FavoriteMatchesComponent implements OnInit {

  constructor(private matchService: MatchTableLoaderService) { }

  ngOnInit(): void {
    this.matchService.populatMatchtableWithFavorites("matches", 0);   
  }
  
}
