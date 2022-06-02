import { HttpClient, HttpHeaders } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { LeagueDto } from '../dto/LeagueDto';
import { MatchDto } from '../dto/MatchDto';
import { SearchDto } from '../dto/SearchDto';
import { TeamDto } from '../dto/TeamDto';

const URL = "http://localhost:8080/AmadeusSports"
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',

  })
};

@Injectable({
  providedIn: 'root'
})
export class SearchService {


  constructor(private http: HttpClient) { }

  public matchHistoryLoaded = new EventEmitter<MatchDto[]>();
  public matchUpcomingLoaded = new EventEmitter<MatchDto[]>();

  public leagueLoaded = new EventEmitter<LeagueDto>();
  public teamLoaded = new EventEmitter<TeamDto>();
  public searchDto!: SearchDto;

  showDataForSearch(searchDto: SearchDto) {
    if (searchDto.type === 'league') {
      let entityDto: LeagueDto;
      this.leagueLoaded.subscribe(data => {
        entityDto = data;
        this.getHistoryMatches(searchDto, entityDto.id);
        this.getUpcomingMatches(searchDto, entityDto.id);
      });
      this.getLeagueByName(searchDto);

    } else {
      let entityDto: TeamDto;
      this.teamLoaded.subscribe(data => {
        entityDto = data;
        console.log(entityDto);
        this.getHistoryMatches(searchDto, entityDto.id);
        this.getUpcomingMatches(searchDto, entityDto.id);
      });
      this.getTeamByName(searchDto);
    }
    this.searchDto = searchDto;

  }


  getHistoryMatches(searchDto: SearchDto, id: number) {
    let url = URL;
    if (searchDto.type === 'league') {
      url += '/league/past/' + id
    } else {
      url += '/team/matches-history/' + id
    }
    this.http.get<MatchDto[]>(url).subscribe(response => {
      this.matchHistoryLoaded.emit(response);
    });
  }
  getUpcomingMatches(searchDto: SearchDto, id: number) {
    let url = URL;
    if (searchDto.type === 'league') {
      url += '/league/upcoming/' + id
    } else {
      url += '/team/matches-upcoming/' + id
    }
    this.http.get<MatchDto[]>(url).subscribe(response => {
      this.matchUpcomingLoaded.emit(response);
    });
  }

  getTeamByName(searchDto: SearchDto): TeamDto {
    let url = URL + "/team/getTeamByName/" + searchDto.name;
    let team!: TeamDto;
    this.http.get<TeamDto>(url).subscribe(response => {
      team = response;
      this.teamLoaded.emit(team);
    });
    return team;
  }

  getLeagueByName(searchDto: SearchDto): LeagueDto {
    let url = URL + "/league/getLeagueByName/" + searchDto.name;
    let league!: LeagueDto;
    this.http.get<LeagueDto>(url).subscribe(response => {
      league = response;
      this.leagueLoaded.emit(league);
    });
    return league;
  }
}
