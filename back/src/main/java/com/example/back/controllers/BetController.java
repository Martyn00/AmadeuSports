package com.example.back.controllers;

import com.example.back.controllers.dto.BetDto;
import com.example.back.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/AmadeusSports/bet")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BetController {

    private final BetService betService;

    @GetMapping(path = "/history")
    public ArrayList<BetDto> getBetHistory() {
        return betService.getBetHistory();
    }

    @GetMapping(path = "/pending")
    public ArrayList<BetDto> getPendingBets() {
        return betService.getPendingBets();
    }

    @GetMapping(path = "/current")
    public ArrayList<BetDto> getCurrentBets() {
        return betService.getCurrentBets();
    }

    @PostMapping(path = "/add-bet/{matchId}/{userId}/{betType}/{amount}")
    public ResponseEntity<String> addBet(@PathVariable Long matchId, @PathVariable Long userId, @PathVariable int betType, @PathVariable int amount) {
        return betService.addBet(matchId, userId, betType, amount);
    }

    @PostMapping(path = "/accept-bet/{betId}/{betType}")
    public ResponseEntity<String> acceptBet(@PathVariable Long betId, @PathVariable int betType) {
        return betService.acceptBet(betId, betType);
    }
}
