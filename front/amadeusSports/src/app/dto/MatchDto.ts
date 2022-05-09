export class MatchDto {
    time!: number;
    team1!: string;
    team2!: string;
    sport!: string;
    score!: string;
    league!: string;
    details!: string;
    isFavorite!: boolean;

    constructor(time: number, team1: string, team2: string, score:string, sport: string, league: string,
        details: string, isFavorite:boolean) {
        this.time = time;
        this.details = details;
        this.league = league;
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
        this.isFavorite = isFavorite;
    }
}