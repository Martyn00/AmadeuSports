package com.example.back.service;

import com.example.back.models.entities.MatchEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamService {

    List<MatchEntity> getMatchesHistory(Long id);

    ResponseEntity<String> addTeamToFavorites(Long teamId);

    ResponseEntity<String> removeTeamFromFavorites(Long matchId);
}
