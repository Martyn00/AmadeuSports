package com.example.back.controllers.dto;

import com.example.back.models.entities.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MatchDto {
    Long id;
    String time;
    TeamDto team1;
    TeamDto team2;
    String sport;
    String score;
    String country;
    String details;
    Boolean isFavorite;
    LeagueDto league;
    String winner;
}
