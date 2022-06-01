import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AddEventDto } from '../dto/AddEventDto';
import { AddMatchDto } from '../dto/AddMatchDto';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  matchForm = new FormGroup({
    team1Name: new FormControl(''),
    team2Name: new FormControl(''),
    startTime: new FormControl('')
  })
  eventForm = new FormGroup({
    matchId: new FormControl(''),
    goalType: new FormControl(''),  
    time: new FormControl('')
  })

  submitMatch() {
    const result: AddMatchDto = Object.assign({}, this.matchForm.value);
    console.log(result);
  }

  submitEvenet() {
    const result: AddEventDto = Object.assign({}, this.eventForm.value);
  }
}
