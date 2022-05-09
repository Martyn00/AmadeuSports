import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PrincipalComponentLoaderService {
  public stateLoaded = new EventEmitter<string>();

  constructor() { }
  changeState(component: string) {
    this.stateLoaded.emit(component);
  }
}
