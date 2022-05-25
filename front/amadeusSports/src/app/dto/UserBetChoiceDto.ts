export class UserBetChoiceDto {
    user1Id!: number;
    user2Id!: number;
    user1Choice!: number;
    user2Choice!: number;
    constructor(user1Id: number,
        user2Id: number,
        user1Choice: number,
        user2Choice: number) {
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.user2Choice = user2Choice;
        this.user1Choice = user1Choice;
    }
}