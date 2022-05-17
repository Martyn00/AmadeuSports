import { EventEmitter, Injectable } from '@angular/core';
import { MatchDto } from '../dto/MatchDto';
import { PrincipalComponentLoaderService } from './principal-component-loader.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const URL = "http://localhost:8080/AmadeusSports"

@Injectable({
  providedIn: 'root'
})
export class MatchTableLoaderService{
    
  public matchesLoaded = new EventEmitter<MatchDto[]>();
  sendMatches: MatchDto[] = [];
  constructor(private http: HttpClient) { }

  populateMatchTable(pos: number) {
    let url = URL + "/match/" + pos;
    this.http.get<MatchDto[]>(url).subscribe(response => {
      this.sendMatches = response;
      this.matchesLoaded.emit(this.sendMatches);
    });
  }
  changeFavoriteState(element: MatchDto) {
    // url = URL + "/match/" + element.Id+"favorite" + element.isFavorite;
    // this.http.post<>(url);
  }

  populatMatchtableWithFavorites(path: string) {
    this.http.get<MatchDto[]>(URL + "/user/" + path).subscribe(response => {
      this.sendMatches = response;
      this.matchesLoaded.emit(this.sendMatches);
    });
  }
}
