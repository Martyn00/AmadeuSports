import { UserBetChoiceDto } from "./UserBetChoiceDto";

export class SearchDto {
    name: string;
    type:string
    constructor(name:string, type:string) {
        this.name = name;
        this.type = type;
    }
} // call to get matches for a team/league