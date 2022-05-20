import { Injectable, EventEmitter } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BetService {
  public coinsLoaded = new EventEmitter<number>();
  coins: number = 300;

  constructor() { }

  getCoins() {
    this.coinsLoaded.emit(this.coins);
  }
}
