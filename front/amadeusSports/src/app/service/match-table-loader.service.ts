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
  constructor(private loader: PrincipalComponentLoaderService, private http: HttpClient) { }

  populateMatchTable(pos: number) {
    let url = URL + "/match/" + pos;
    this.http.get<MatchDto[]>(url).subscribe(response => {
      this.sendMatches = response;
      this.matchesLoaded.emit(this.sendMatches);
    });
  }
}
