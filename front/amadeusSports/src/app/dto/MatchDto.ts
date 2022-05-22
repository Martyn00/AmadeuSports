import { LeagueDto } from "./LeagueDto";
import { TeamDto } from "./TeamDto";

export class MatchDto {
    id!: number;
    time!: string;
    team1!: TeamDto;
    team2!: TeamDto;
    sport!: string;
    score!: string;
    league!: LeagueDto;
    details!: string;
    isFavorite!: boolean;

    constructor(id: number, time: string, team1: TeamDto, team2: TeamDto, score:string, sport: string, league: LeagueDto,
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