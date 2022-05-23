import { EventEmitter, Injectable } from '@angular/core';
import { MatchDto } from '../dto/MatchDto';
import { PrincipalComponentLoaderService } from './principal-component-loader.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TeamDto } from '../dto/TeamDto';
import { LeagueDto } from '../dto/LeagueDto';

const URL = "http://localhost:8080/AmadeusSports"
const httpOptions = {
  headers: new HttpHeaders(
    {
      'Content-Type': 'application/json',
    },
  )
};
@Injectable({
  providedIn: 'root'
})
export class MatchTableLoaderService {

  public matchesLoaded = new EventEmitter<MatchDto[]>();
  public favoriteMatchesLoaded = new EventEmitter<MatchDto[]>();
  sendMatches: MatchDto[] = [];
  constructor(private http: HttpClient) { }

  populatMatchtableWithFavorites(type: string, id:number) {
    let url = URL;
    if (type === "matches") {
      url = url + "/match/favorites";
    } else if (type === "teams") {
      url = url + "/team/" + id  + "/matches";
    } else if (type === "leagues") {
      url = url + "/league/upcoming/" + id ;
    }

    this.http.get<MatchDto[]>(url, httpOptions).subscribe(response => {
      this.sendMatches = response;
      this.favoriteMatchesLoaded.emit(this.sendMatches);
    });
  }
  populateMatchTable( pos:number) {
    let url = URL + "/match/" + pos;

    console.log(httpOptions.headers);
    this.http.get<MatchDto[]>(url, httpOptions).subscribe(response => {
      this.sendMatches = response;
      this.matchesLoaded.emit(this.sendMatches);
    });
  }
  changeFavoriteState(element: MatchDto) {
    let url;
    if (element.isFavorite) {
      url = URL + "/match/" + element.id + "/favorites-add";

    } else {
      url = URL + "/match/" + element.id + "/favorites-remove";
    }

    this.http.post<any>(url, null, httpOptions).subscribe(response => {

    });
  }

}
