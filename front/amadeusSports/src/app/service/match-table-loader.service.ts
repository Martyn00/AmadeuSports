import { EventEmitter, Injectable } from '@angular/core';
import { MatchDto } from '../dto/MatchDto';
import { PrincipalComponentLoaderService } from './principal-component-loader.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const URL = "http://localhost:8080/AmadeusSports"
const httpOptions = {
  headers: new HttpHeaders(
    {
      'Content-Type': 'application/json',
    }
  )
};
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
    let url = URL + "/match/" + element.id+"/favorite/" + element.isFavorite;
    this.http.post<any>(url, null, httpOptions).subscribe(response => {
      
    });
  }

  populatMatchtableWithFavorites(path: string) {
    this.http.get<MatchDto[]>(URL + "/user/" + path).subscribe(response => {
      this.sendMatches = response;
      this.matchesLoaded.emit(this.sendMatches);
    });
  }
}
