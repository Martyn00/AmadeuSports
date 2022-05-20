import { Component, Input, OnInit } from '@angular/core';
import { MatButtonToggle } from '@angular/material/button-toggle';
import { MatchTableLoaderService } from '../service/match-table-loader.service';
@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
  
export class FavoritesComponent implements OnInit {
  constructor(private matchTableService: MatchTableLoaderService) { }
  ngOnInit(): void {
    this.matchTableService.populatMatchtableWithFavorites('favorite-matches');
  }
  onValChange(path: string) {
    this.matchTableService.populatMatchtableWithFavorites(path);
  }
}
