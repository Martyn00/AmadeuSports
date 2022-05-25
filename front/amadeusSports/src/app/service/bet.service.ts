import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, EventEmitter } from '@angular/core';
import { BetDto } from '../dto/BetDto';
import { ResultDto } from '../dto/ResultDto';

const URL = "http://localhost:8080/AmadeusSports"
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};
@Injectable({
  providedIn: 'root'
})
export class BetService {

  public coinsLoaded = new EventEmitter<number>();
  coins: number = 300;
  sendBets!: BetDto[];
  public betsHistoryLoaded = new EventEmitter<BetDto[]>();
  public betsCurrentLoaded = new EventEmitter<BetDto[]>();
  public betsPendingLoaded = new EventEmitter<BetDto[]>();
  constructor(private http: HttpClient) { }

  getCoins() {
    this.coinsLoaded.emit(this.coins);
  }
  bet(result: ResultDto) {
    this.http.post<any>(URL + '/add-bet/' + result.userId + '/' + result.choice + '/' + result.coins, httpOptions)
  }
  getUserBets(type: string) {
    let url = URL + "/bet/" + type;

    this.http.get<BetDto[]>(url).subscribe(response => {
      this.sendBets = response;
      console.log("aici " + this.sendBets);
      if (type === 'history') {
        this.betsHistoryLoaded.emit(this.sendBets);
      }
      if (type === 'current') {
        this.betsCurrentLoaded.emit(this.sendBets);
      }
      if (type === 'history') {
        this.betsPendingLoaded.emit(this.sendBets);
      }
    });
  }
}
