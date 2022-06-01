import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddEventDto } from '../dto/AddEventDto';
import { AddMatchDto } from '../dto/AddMatchDto';
import { DatePipe } from '@angular/common'

const URL = "http://localhost:8080/AmadeusSports"
const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient, private datepipe: DatePipe) { }

  addMatch(match: AddMatchDto) {
    let url = URL + '/match/add-match';
  
    match.startTime = this.datepipe.transform(match.startTime, 'YYYY-MM-dd hh:mm') || '';
    this.http.post<any>(url, match, httpOptions).subscribe(data => {
      console.log(data);
    })
  }
  addEvent(event: AddEventDto) {
    let url = URL + '/match/add-event'
    console.log(event);
    this.http.post<any>(url, event, httpOptions).subscribe(data => {
      console.log(data);
    })
  }
}
