package com.example.back.service;

import com.example.back.controllers.dto.BetDto;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface BetService {
    ResponseEntity<ArrayList<BetDto>> getBetHistory();
    ResponseEntity<ArrayList<BetDto>> getPendingBets();
    ResponseEntity<ArrayList<BetDto>> getCurrentBets();
    ResponseEntity<String> addBet(Long matchId, Long userId, int betType, int amount);
    ResponseEntity<String> acceptBet(Long betId, int betType);
    ResponseEntity<String> cancelBet(Long betId);
}
