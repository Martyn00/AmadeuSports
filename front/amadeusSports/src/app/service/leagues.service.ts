import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { LeagueDto } from '../dto/LeagueDto';
import { TeamDto } from '../dto/TeamDto';

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
export class LeaguesService {

  constructor(private http: HttpClient) { }
  public leaguesLoaded = new EventEmitter<LeagueDto[]>();
  public favoriteChanged = new EventEmitter<LeagueDto>();
  sendLeagues!: TeamDto[];
  changeFavoriteStateLeague(league: LeagueDto) {
    let url;
    if (!league.isFavorite) {
      url = URL + "/league/favorites-add/" + league.id;

    } else {
      url = URL + "/league/favorites-remove/" + league.id;
    }
    this.http.post<any>(url, null, httpOptions).subscribe(response => {
      this.favoriteChanged.emit(league);
    });
  }
  getAllFavoriteLeagues() {
    let path = "/league/favorites"
    this.http.get<TeamDto[]>(URL + path, httpOptions).subscribe(response => {
      this.sendLeagues = response;
      this.leaguesLoaded.emit(this.sendLeagues);
    });
  }

  getleagueByName(league: string){
    let url = URL + "/getLeagueByName/" + league;
    return this.http.get<LeagueDto>(url, httpOptions)
  }
}
