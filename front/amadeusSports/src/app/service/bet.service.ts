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
    console.log(result);
    this.http.post<any>(URL + '/bet/add-bet/' + result.matchId + '/' + result.userId + '/' + result.choice + '/' + result.coins, httpOptions).subscribe(() => {
      console.log("responded");
    })

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
      if (type === 'pending') {
        this.betsPendingLoaded.emit(this.sendBets);
      }
    });
  }

  acceptBet(result: number, element: BetDto) {
    let url = URL + '/bet/accept-bet/' + element.id + "/" + result;
    this.http.post<any>(url, httpOptions).subscribe(() => {
      console.log("responded");
    });
  }

  cancelBet(element: BetDto) {
    let url = URL + '/bet/cancel-bet/' + element.id
    console.log(url);
    this.http.post<any>(url, httpOptions).subscribe(() => {
      console.log("responded");
    });
  }

}
