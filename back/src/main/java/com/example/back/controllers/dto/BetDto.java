package com.example.back.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BetDto {
    private Long id;
    private String time;
    private TeamDto team1;
    private TeamDto team2;
    private String score;
    private String sport;
    private LeagueDto league;
    private int amount;
    private String status;
    private int result;
    private UserBetChoiceDto betChoice;
}
