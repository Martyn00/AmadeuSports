import { EventEmitter, Injectable } from '@angular/core';
import { MatchDto } from '../dto/MatchDto';
import { PrincipalComponentLoaderService } from './principal-component-loader.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
  sendMatches: MatchDto[] = [];
  constructor(private http: HttpClient) { }

  populateMatchTable(pos: number) {
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

  populatMatchtableWithFavorites(path: string) {
    this.http.get<MatchDto[]>(URL + path, httpOptions).subscribe(response => {
      this.sendMatches = response;
      this.matchesLoaded.emit(this.sendMatches);
    });
  }
  populatMatchtableWithFavoriteMatches() {
    this.http.get<MatchDto[]>(URL + "/match/favorites", httpOptions).subscribe(response => {
      this.sendMatches = response;
      console.log(this.sendMatches);
      this.matchesLoaded.emit(this.sendMatches);
    });
  }
}
