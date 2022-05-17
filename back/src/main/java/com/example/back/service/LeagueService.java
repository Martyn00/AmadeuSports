package com.example.back.service;

public interface LeagueService {
    String addLeagueToFavorites(Long leagueId);

    String removeLeagueFromFavorites(Long leagueId);
}
