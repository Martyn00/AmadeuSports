export class BetDto {
    id!: number;
    time!: string;
    team1!: string;
    team2!: string;
    score!: string;
    sport!: string;
    league!: string;
    amount!: number;
    status!: string;
    result!: number;
    constructor(id: number, time: string, team1: string, team2: string, score: string, sport: string, league: string,
        amount: number, status: string, result: number) {
        this.time = time;
        this.amount = amount;
        this.league = league;
        this.sport = sport;
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
        this.status = status;
        this.result = result;
    }
}