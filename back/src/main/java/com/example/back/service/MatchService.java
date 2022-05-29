package com.example.back.service;

import com.example.back.controllers.dto.AddEventDto;
import com.example.back.controllers.dto.AddMatchDto;
import com.example.back.controllers.dto.MatchDto;
import com.example.back.models.entities.MatchEntity;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface MatchService {
    ResponseEntity<List<MatchDto>> getMatchByDate(Integer data);

    ResponseEntity<String> addMatchToFavorites(Long matchID);

    ResponseEntity<String> removeMatchFromFavorites(Long matchID);

    ResponseEntity<List<MatchDto>> getFavoriteMatches();

    MatchDto mapToMatchDto(MatchEntity matchEntity);
    ResponseEntity<String> addMatch(AddMatchDto addMatchDto);
    ResponseEntity<String> addEvent(AddEventDto addEventDto);
    ResponseEntity<MatchDto> updateMatch(Long matchId);
    void updateAllMatches();
    void sortAscendingByDate(ArrayList<MatchDto> result);
    void sortDescendingByDate(ArrayList<MatchDto> result);
}
