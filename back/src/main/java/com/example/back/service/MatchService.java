package com.example.back.service;

import com.example.back.controllers.dto.MatchDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MatchService {
    List<MatchDto> getMatchByDate(Integer data);

    ResponseEntity<String> addMatchToFavorites(Long matchID);

    ResponseEntity<String> removeMatchFromFavorites(Long matchID);
}
