package com.example.back.service;

import com.example.back.controllers.dto.MatchDto;
import com.example.back.controllers.dto.TeamDto;
import com.example.back.models.entities.MatchEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamService {

    List<MatchDto> getMatchesHistory(Long id);

    ResponseEntity<String> addTeamToFavorites(Long teamId);

    ResponseEntity<String> removeTeamFromFavorites(Long matchId);

    ResponseEntity<TeamDto> getTeamByName(String teamName);
  
    List<TeamDto> getFavoriteTeams();

}
