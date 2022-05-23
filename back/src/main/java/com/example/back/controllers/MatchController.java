package com.example.back.controllers;

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

    @GetMapping(path = "/{data}")
    public List<MatchDto> getMatchByDate(@PathVariable Integer data) {
        return matchService.getMatchByDate(data);
    }

    @PostMapping(path = "/{matchID}/favorites-add")
    public ResponseEntity<String> addMatchToFavorites(@PathVariable Long matchID) {
        return matchService.addMatchToFavorites(matchID);
    }

    @PostMapping(path = "/{matchID}/favorites-remove")
    public ResponseEntity<String> removeMatchFromFavorites(@PathVariable Long matchID) {
        return matchService.removeMatchFromFavorites(matchID);
    }

    @GetMapping(path = "/favorites")
    public List<MatchDto> getFavoriteMatches() {
        return matchService.getFavoriteMatches();
    }

    @PostMapping(path = "/add-match/{team1Name}/{team2Name}/{startTime}/{result}")
    public ResponseEntity<String> addMatch(@PathVariable String team1Name, @PathVariable String team2Name,
                                           @PathVariable String startTime, @PathVariable String result) {
        return matchService.addMatch(team1Name, team2Name, startTime, result);
    }

    @PostMapping(path = "/add-event/{matchId}/{goal}/{min}")
    public ResponseEntity<String> addEvent(@PathVariable Long matchId, @PathVariable int goal, @PathVariable int min) {
        return matchService.addEvent(matchId, goal, min);
    }

}
