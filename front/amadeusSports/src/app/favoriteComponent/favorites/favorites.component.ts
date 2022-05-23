import { Component, Input, OnInit } from '@angular/core';
import { MatButtonToggle } from '@angular/material/button-toggle';
import { MatchTableLoaderService } from '../../service/match-table-loader.service';
@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
  
export class FavoritesComponent implements OnInit {
  path: string =  "/match/favorites";
  constructor(private matchTableService: MatchTableLoaderService) { }
  ngOnInit(): void {

  }
  onValChange(path: string) {
    this.path = path;
    if (path === "/favorites/matches") {
      this.matchTableService.populatMatchtableWithFavorites("matches", 0);
    }
  }
}
