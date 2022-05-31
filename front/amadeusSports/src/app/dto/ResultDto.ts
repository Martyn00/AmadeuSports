export class ResultDto {
    coins!: number;
    userId!: number;
    choice!: number;
    matchId!: number;
    constructor(coins: number, userId: number, choice: number) {
        this.choice = choice;
        this.coins = coins;
        this.userId = userId;
    }
}