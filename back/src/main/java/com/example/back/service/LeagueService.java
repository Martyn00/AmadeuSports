package com.example.back.service;

import com.example.back.controllers.dto.LeagueDto;
import com.example.back.controllers.dto.MatchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

public interface LeagueService {
    ResponseEntity<String> addLeagueToFavorites(Long leagueId);

    ResponseEntity<String> removeLeagueFromFavorites(Long leagueId);

    ResponseEntity<ArrayList<LeagueDto>> getFavoriteLeagues();

    ResponseEntity<ArrayList<MatchDto>> getUpcomingMatchesByLeagueId(Long leagueId);

    ResponseEntity<ArrayList<MatchDto>> getPastMatchesByLeagueId(Long leagueId);

    ResponseEntity<LeagueDto> getLeagueByName(String leagueName);
}
