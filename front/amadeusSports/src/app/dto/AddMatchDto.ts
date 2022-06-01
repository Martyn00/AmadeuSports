export class AddMatchDto {
    team1Name!:string;
    team2Name!:string;
    startTime!: string;
    score!: String;
    constructor(team1Name:string, team2Name:string, startTime:string) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.startTime = startTime;
        this.score = '-';
    }
}