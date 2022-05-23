import { Component, Inject, OnInit, Optional } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserDto } from 'src/app/dto/UserDto';
import { FriendService } from 'src/app/service/friend.service';

@Component({
  selector: 'app-bet-dialog',
  templateUrl: './bet-dialog.component.html',
  styleUrls: ['./bet-dialog.component.css']
})
export class BetDialogComponent implements OnInit {

  fromPage!: string;
  fromDialog!: string;
  checkBoxesDisabled!: boolean[];
  checkedBoxes!: boolean[];
  friends!: UserDto[];
  form = new FormControl();
  constructor(
    public dialogRef: MatDialogRef<BetDialogComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public mydata: any,
    private friendsService:FriendService
  ) { }

  ngOnInit(): void {
    this.fromDialog = "I am from dialog land...";
    this.checkBoxesDisabled = [false, false, false];
    this.checkedBoxes = [false, false, false];
    this.friendsService.friendsLoaded.subscribe(data => {
      this.friends = data;
    })
    this.friendsService.populateFriendsTable();
    console.log("friends:" + this.friends);
  }
  checkedABox(pos: number) {
    for (let i = 0; i < 3; i++){
      this.checkedBoxes[i] = false;
    }
    this.checkedBoxes[pos] = true;
  }
  closeDialog() { this.dialogRef.close({ event: 'close', data: this.fromDialog }); }
}
