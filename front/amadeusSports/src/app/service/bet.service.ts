import { HttpClient, HttpEvent, HttpEventType, HttpHeaders } from '@angular/common/http';
import { Injectable, EventEmitter, Output } from '@angular/core';
import { upperCase } from '@rxweb/reactive-form-validators';
import { BetDto } from '../dto/BetDto';
import { ResultDto } from '../dto/ResultDto';

const URL = "http://localhost:8080/AmadeusSports"
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',

  })
};
const httpOptionsText = {
  headers: new HttpHeaders({
    'Content-Type': 'text/plain',

  })
};
@Injectable({
  providedIn: 'root'
})
export class BetService {

  public coinsLoaded: EventEmitter<number> = new EventEmitter<number>();
  coins!: number;
  sendBets!: BetDto[];

  public betsHistoryLoaded = new EventEmitter<BetDto[]>();
  public betsCurrentLoaded = new EventEmitter<BetDto[]>();
  public betsPendingLoaded = new EventEmitter<BetDto[]>();
  constructor(private http: HttpClient) { }

  getCoins() {
    let url = URL + "/user/wallet"
    this.http.get<number>(url).subscribe(response => {
      this.coins = response;
      this.coinsLoaded.emit(this.coins);
    });
  }
  bet(result: ResultDto) {
    this.http.post<any>(URL + '/bet/add', result, httpOptions).subscribe(
      {
        next: (resp) => {
          this.getCoins();
        },
        error: (err) => {
          console.log(err);
        }
      }
    )
  }

  getUserBets(type: string) {
      let url = URL + "/bet/" + type;

      this.http.get<BetDto[]>(url).subscribe(response => {
        this.sendBets = response;
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

  acceptBet(result: string, element: BetDto) {
      let url = URL + '/bet/accept/' + element.id + "/" + result.toUpperCase();
      this.http.post<any>(url, httpOptions).subscribe(() => {
        console.log("responded");
        this.getCoins();
        this.getUserBets('pending');
      });
      
    }

  cancelBet(element: BetDto) {
      let url = URL + '/bet/cancel/' + element.id
    console.log(url);
      this.http.post<any>(url, httpOptions).subscribe(() => {
        console.log("responded");
        this.getCoins();
        this.getUserBets('pending');
      });
      
    }

}
