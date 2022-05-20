package com.example.back.service;

import com.example.back.controllers.dto.LeagueDto;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface LeagueService {
    ResponseEntity<String> addLeagueToFavorites(Long leagueId);

    ResponseEntity<String> removeLeagueFromFavorites(Long leagueId);
    ArrayList<LeagueDto> getFavoriteLeagues();

}
