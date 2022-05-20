package com.example.back.service;

import com.example.back.models.entities.MatchEntity;

import java.util.List;

public interface TeamService {

    List<MatchEntity> getMatchesHistory(Long id);

    String addTeamToFavorites(Long teamId);

    String removeTeamFromFavorites(Long matchId);
}
