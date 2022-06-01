import { EventEmitter, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { TeamDto } from '../dto/TeamDto';
import { MatchTableLoaderService } from './match-table-loader.service';

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
export class TeamsService {

  constructor(private http: HttpClient, private matchTableService: MatchTableLoaderService) { }

  public teamsLoaded = new EventEmitter<TeamDto[]>();
  public favoriteChanged = new EventEmitter<TeamDto>();
  sendTeams!: TeamDto[];

  changeFavoriteStateTeam(team: TeamDto) {
    let url;
    if (!team.isFavorite) {
      url = URL + "/team/favorites-add/" + team.id;

    } else {
      url = URL + "/team/favorites-remove/" + team.id;
    }
    console.log(url)
    this.http.post<any>(url, null, httpOptions).subscribe(response => {  
    });
    this.favoriteChanged.emit(team);
  }
  getAllFavoriteTeams() {
    let url = URL +"/team/favorites";
    this.http.get<TeamDto[]>(url, httpOptions).subscribe(response => {
      this.sendTeams = response;
      console.log(this.sendTeams);
      this.teamsLoaded.emit(this.sendTeams);
    });
  }

  getTeamByName(team: string) {
    let url = URL + "/getTeamByName/" + team;
    return this.http.get<TeamDto>(url, httpOptions)
  }

}
