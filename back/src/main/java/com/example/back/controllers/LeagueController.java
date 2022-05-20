package com.example.back.controllers;

import com.example.back.controllers.dto.LeagueDto;
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

    private final LeagueServiceImpl leagueService;

    @PostMapping(path = "/{leagueId}/favorites-add")
    public ResponseEntity<String> addTeamToFavorites(@PathVariable Long leagueId) {
        return leagueService.addLeagueToFavorites(leagueId);
    }

    @PostMapping(path = "/{leagueId}/favorites-remove")
    public ResponseEntity<String> removeTeamFromFavorites(@PathVariable Long leagueId) {
        return leagueService.removeLeagueFromFavorites(leagueId);
    }

    @GetMapping(path = "favorites")
    public ArrayList<LeagueDto> getFavoriteTeams() {
        return leagueService.getFavoriteLeagues();
    }
}
