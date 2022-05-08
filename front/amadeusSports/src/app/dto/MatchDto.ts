export class MatchDto {
    time!: number;
    team1!: string;
    team2!: string;
    sport!: string;
    league!: string;
    details!: string;

    constructor(time: number, team1: string, team2: string, sport: string, league: string,
    details:string) {
        this.time = time;
        this.details = details;
        this.league = league;
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
    }
}