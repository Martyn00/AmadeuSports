import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AddEventDto } from '../dto/AddEventDto';
import { AddMatchDto } from '../dto/AddMatchDto';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
  }
  matchForm = new FormGroup({
    team1Name: new FormControl(''),
    team2Name: new FormControl(''),
    startTime: new FormControl('')
  })
  eventForm = new FormGroup({
    matchId: new FormControl(''),
    goal: new FormControl(''),  
    minute: new FormControl('')
  })

  submitMatch() {
    const result: AddMatchDto = Object.assign({}, this.matchForm.value);
    this.adminService.addMatch(result);
  }

  submitEvent() {
    const result: AddEventDto = Object.assign({}, this.eventForm.value);
    this.adminService.addEvent(result);
  }
}
