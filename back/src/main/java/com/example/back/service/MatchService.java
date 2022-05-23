package com.example.back.service;

import com.example.back.controllers.dto.MatchDto;
import com.example.back.models.entities.MatchEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface MatchService {
    List<MatchDto> getMatchByDate(Integer data);

    ResponseEntity<String> addMatchToFavorites(Long matchID);

    ResponseEntity<String> removeMatchFromFavorites(Long matchID);

    List<MatchDto> getFavoriteMatches();

    MatchDto mapToMatchDto(MatchEntity matchEntity);
    ResponseEntity<String> addMatch(String team1Name, String team2Name,
                                    String startTime, String result);
    ResponseEntity<String> addEvent(Long matchId, int goal, int min);
    MatchDto updateMatch(Long matchId);
    void updateListOfMatches(Set<MatchEntity> matches);
    void sortAscendingByDate(ArrayList<MatchDto> result);
    void sortDescendingByDate(ArrayList<MatchDto> result);
}
