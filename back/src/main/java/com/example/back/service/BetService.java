package com.example.back.service;

import com.example.back.controllers.dto.BetDto;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface BetService {
    ArrayList<BetDto> getBetHistory();
    ArrayList<BetDto> getPendingBets();
    ArrayList<BetDto> getCurrentBets();
    ResponseEntity<String> addBet(Long matchId, Long userId, int betType, int amount);
    ResponseEntity<String> acceptBet(Long betId, int betType);
}
