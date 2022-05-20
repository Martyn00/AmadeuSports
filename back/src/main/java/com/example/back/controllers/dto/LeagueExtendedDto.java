package com.example.back.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeagueExtendedDto extends LeagueDto{
    List<MatchDto> pastMatches;
    List<MatchDto> futureMatches;
}
