package com.example.back.controllers;

import com.example.back.controllers.dto.AddEventDto;
import com.example.back.controllers.dto.AddMatchDto;
import com.example.back.controllers.dto.MatchDto;
import com.example.back.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/AmadeusSports/match")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MatchController {

    private final MatchService matchService;

    @GetMapping(path = "/{daysPast}")
    public ResponseEntity<List<MatchDto>> getMatchByDate(@PathVariable Integer daysPast) {
        return matchService.getMatchByDate(daysPast);
    }

    @PostMapping(path = "/favorites-add/{matchID}")
    public ResponseEntity addMatchToFavorites(@PathVariable Long matchID) {
        return matchService.addMatchToFavorites(matchID);
    }

    @PostMapping(path = "/favorites-remove/{matchID}")
    public ResponseEntity removeMatchFromFavorites(@PathVariable Long matchID) {
        return matchService.removeMatchFromFavorites(matchID);
    }

    @GetMapping(path = "/favorites")
    public ResponseEntity<List<MatchDto>> getFavoriteMatches() {
        return matchService.getFavoriteMatches();
    }

    @PostMapping(path = "/add-match")
    public ResponseEntity addMatch(@RequestBody AddMatchDto addMatchDto) {
        return matchService.addMatch(addMatchDto);
    }

    @PostMapping(path = "/add-event")
    public ResponseEntity addEvent(@RequestBody AddEventDto addEventDto) {
        return matchService.addEvent(addEventDto);
    }

    @GetMapping(path = "/update-match/{matchId}")
    public ResponseEntity<MatchDto> updateMatch(@PathVariable Long matchId) {
        return matchService.updateMatch(matchId);
    }

}
