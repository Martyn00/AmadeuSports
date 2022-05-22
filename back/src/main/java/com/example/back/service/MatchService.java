package com.example.back.service;

import com.example.back.controllers.dto.MatchDto;
import com.example.back.models.entities.MatchEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatchService {
    List<MatchDto> getMatchByDate(Integer data);

    ResponseEntity<String> addMatchToFavorites(Long matchID);

    ResponseEntity<String> removeMatchFromFavorites(Long matchID);

    List<MatchDto> getFavoriteMatches();

    MatchDto mapToMatchDto(MatchEntity matchEntity);
}
