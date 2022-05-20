package com.example.back.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MatchDto {
    String time;
    String team1;
    String team2;
    String sport;
    String score;
//    @JsonProperty(value = "league")
    String country;
    String details;
    Boolean isFavorite;
    String league;
    String winner;
}
