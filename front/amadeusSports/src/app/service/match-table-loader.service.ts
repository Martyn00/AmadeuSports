import { EventEmitter, Injectable } from '@angular/core';
import { MatchDto } from '../dto/MatchDto';
import { PrincipalComponentLoaderService } from './principal-component-loader.service';

const ELEMENT_DATA: MatchDto[] = [
  new MatchDto(1, 'PSG', 'Saint Etienne', '3-0', 'football', 'Ligue 1', 'asd', false),
  new MatchDto(2, 'PSG', 'Saint Etienne', '2-1', 'football', 'Ligue 1', 'asd', false),
  new MatchDto(3, 'PSG', 'Saint Etienne', '3-0', 'football', 'Ligue 1', 'asd', false)
];
@Injectable({
  providedIn: 'root'
})
export class MatchTableLoaderService{

  public matchesLoaded = new EventEmitter<MatchDto[]>();
  matches: MatchDto[] = ELEMENT_DATA;
  sendMatches: MatchDto[] = [];
  constructor(private loader:PrincipalComponentLoaderService) { }

  populateMatchTable(pos: number) {
    this.sendMatches = [this.matches[pos]]
    this.matchesLoaded.emit(this.sendMatches);
  }
}
