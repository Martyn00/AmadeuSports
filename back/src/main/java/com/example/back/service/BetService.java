package com.example.back.service;

import com.example.back.controllers.dto.AddBetDto;
import com.example.back.controllers.dto.BetDto;
import com.example.back.models.entities.BetType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface BetService {
    ResponseEntity<ArrayList<BetDto>> getBetHistory();
    ResponseEntity<ArrayList<BetDto>> getPendingBets();
    ResponseEntity<ArrayList<BetDto>> getCurrentBets();
    ResponseEntity<String> addBet(AddBetDto addBetDto);
    ResponseEntity<String> acceptBet(Long betId, BetType betType);
    ResponseEntity<String> cancelBet(Long betId);
}
