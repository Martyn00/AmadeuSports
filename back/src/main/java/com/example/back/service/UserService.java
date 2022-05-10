package com.example.back.service;

import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;

import java.util.List;

public interface UserService {

    List<MatchEntity> getFavoriteMatchesByUserId(Long id);

    List<Team> getFavoriteTeamsByUserId(Long id);

    List<League> getFavoriteLeaguesByUserId(Long id);
}
