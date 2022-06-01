import { Component, Inject, OnInit, Optional } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ResultDto } from 'src/app/dto/ResultDto';
import { UserDto } from 'src/app/dto/UserDto';
import { FriendService } from 'src/app/service/friend.service';

@Component({
  selector: 'app-bet-dialog',
  templateUrl: './bet-dialog.component.html',
  styleUrls: ['./bet-dialog.component.css']
})
export class BetDialogComponent implements OnInit {

  fromPage!: string;
  checkBoxesDisabled!: boolean[];
  checkedBoxes!: boolean[];
  friends!: UserDto[];
  friendForm = new FormControl();
  coinForm = new FormControl();
  result!: ResultDto;
  constructor(
    public dialogRef: MatDialogRef<BetDialogComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public mydata: any,
    private friendsService: FriendService,
  ) {
    this.result = new ResultDto(-1, '', 'PENDING');
   }

  ngOnInit(): void {
    this.checkBoxesDisabled = [false, false, false];
    this.checkedBoxes = [false, false, false];
    this.friendsService.friendsLoaded.subscribe(data => {
      this.friends = data;
    })
    this.friendsService.populateFriendsTable();
    this.result.matchId = this.mydata;

  }
  checkedABox(pos: number) {
    for (let i = 0; i < 3; i++){
      this.checkedBoxes[i] = false;
    }
    this.checkedBoxes[pos] = true;
    if (pos == 0) {
      this.result.betType = 'EQUAL';
    } else if (pos == 1) {
      this.result.betType = 'HOME';
    } else if (pos == 2) {
      this.result.betType = 'AWAY';
    }
  }

  changeUsername(username: string) {
    this.result.username = username;
    console.log(this.result);
  }

  changeCoin() {
    this.result.amount = this.coinForm.value;
    console.log(this.result.amount);
  }
}



