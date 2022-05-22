package com.example.back.service;

import com.example.back.controllers.dto.LeagueDto;
import com.example.back.controllers.dto.MatchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

public interface LeagueService {
    ResponseEntity<String> addLeagueToFavorites(Long leagueId);

    ResponseEntity<String> removeLeagueFromFavorites(Long leagueId);
    ArrayList<LeagueDto> getFavoriteLeagues();
    ArrayList<MatchDto> getUpcomingMatchesByLeagueId(Long leagueId);
    ArrayList<MatchDto> getPastMatchesByLeagueId(Long leagueId);

}
