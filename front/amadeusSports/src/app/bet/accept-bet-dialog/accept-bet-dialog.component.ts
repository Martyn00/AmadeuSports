import { Component, Inject, OnInit, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { BetDto } from 'src/app/dto/BetDto';

@Component({
  selector: 'app-accept-bet-dialog',
  templateUrl: './accept-bet-dialog.component.html',
  styleUrls: ['./accept-bet-dialog.component.css']
})
export class AcceptBetDialogComponent implements OnInit {
  result1!: number;
  result2!: number;
  result1Name!: string;
  result2Name!: string;
  betData!: BetDto;
  choiceData!: number;
  cancel = -1;
  constructor(@Optional() @Inject(MAT_DIALOG_DATA) public mydata: any, public dialogRef: MatDialogRef<any>) {
    this.choiceData = mydata[1];
    this.betData = mydata[0];
    if (this.choiceData === 0) {
      this.result1Name = 'equal';
      this.result2Name = 'away';
      this.result1 = 1;
      this.result2 = 2;
    } else if (this.choiceData === 1) {
      this.result1Name = 'home';
      this.result2Name = 'equal';
      this.result1 = 0;
      this.result2 = 2;
    } else if (this.choiceData === 2) {
      this.result1Name = 'home';
      this.result2Name = 'away';
      this.result1 = 0;
      this.result2 = 1;
    }
   }

  ngOnInit(): void {
    this.dialogRef.updateSize('30%', '50%');
  }

}
