export class ResultDto {
    amount!: number;
    username!: string;
    betType!: string;
    matchId!: number;
    constructor(coins: number, username: string, betType: string) {
        this.betType = betType;
        this.amount = coins;
        this.username = username;
    }
}