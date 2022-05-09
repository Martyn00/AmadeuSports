import { Component, Input, OnInit } from '@angular/core';
import { MatButtonToggle } from '@angular/material/button-toggle';
@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  types = ['teams', 'matches', 'league'];
  periods = ['today', 'yesterday', 'tomorrow']
  constructor() { }
  ngOnInit(): void {
  }

}
