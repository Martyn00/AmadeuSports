package com.example.back.controllers;

import com.example.back.controllers.dto.AddBetDto;
import com.example.back.controllers.dto.BetDto;
import com.example.back.models.entities.BetType;
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
    public ResponseEntity<ArrayList<BetDto>> getBetHistory() {
        return betService.getBetHistory();
    }

    @GetMapping(path = "/pending")
    public ResponseEntity<ArrayList<BetDto>> getPendingBets() {
        return betService.getPendingBets();
    }

    @GetMapping(path = "/current")
    public ResponseEntity<ArrayList<BetDto>> getCurrentBets() {
        return betService.getCurrentBets();
    }

    @PostMapping(path = "/add")
    public ResponseEntity addBet(@RequestBody AddBetDto addBetDto) {
        return betService.addBet(addBetDto);
    }

    @PostMapping(path = "/accept/{betId}/{betType}")
    public ResponseEntity acceptBet(@PathVariable Long betId, @PathVariable BetType betType) {
        return betService.acceptBet(betId, betType);
    }

    @PostMapping(path = "/cancel/{betId}")
    public ResponseEntity cancelBet(@PathVariable Long betId) {
        return betService.cancelBet(betId);
    }
}
