package com.example.back.controllers;

import com.example.back.controllers.dto.LeagueDto;
import com.example.back.controllers.dto.MatchDto;
import com.example.back.service.LeagueService;
import com.example.back.service.LeagueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/AmadeusSports/league")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LeagueController {

    private final LeagueService leagueService;

    @PostMapping(path = "/favorites-add/{leagueId}")
    public ResponseEntity<String> addLeagueToFavorites(@PathVariable Long leagueId) {
        return leagueService.addLeagueToFavorites(leagueId);
    }

    @PostMapping(path = "/favorites-remove/{leagueId}")
    public ResponseEntity<String> removeLeagueFromFavorites(@PathVariable Long leagueId) {
        return leagueService.removeLeagueFromFavorites(leagueId);
    }

    @GetMapping(path = "/favorites")
    public ResponseEntity<ArrayList<LeagueDto>> getFavoriteLeagues() {
        return leagueService.getFavoriteLeagues();
    }

    @GetMapping(path = "/upcoming/{leagueId}")
    public ResponseEntity<ArrayList<MatchDto>> getUpcomingMatchesByLeagueId(@PathVariable Long leagueId) {
        return leagueService.getUpcomingMatchesByLeagueId(leagueId);
    }

    @GetMapping(path = "/past/{leagueId}")
    public ResponseEntity<ArrayList<MatchDto>> getPastMatchesByLeagueId(@PathVariable Long leagueId) {
        return leagueService.getPastMatchesByLeagueId(leagueId);
    }

    @GetMapping(path = "/getLeagueByName/{leagueName}")
    public ResponseEntity<LeagueDto> getPastMatchesByLeagueId(@PathVariable String leagueName) {
        return leagueService.getLeagueByName(leagueName);
    }
}
