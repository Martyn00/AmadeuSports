package com.example.back.controllers;

import com.example.back.service.LeagueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/AmadeusSports/league")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LeagueController {

    private final LeagueServiceImpl leagueService;

    @PostMapping(path = "/{leagueId}/favorites-add")
    public String addTeamToFavorites(@PathVariable Long leagueId) {
        return leagueService.addLeagueToFavorites(leagueId);
    }

    @PostMapping(path = "/{leagueId}/favorites-remove")
    public String removeTeamFromFavorites(@PathVariable Long leagueId) {
        return leagueService.removeLeagueFromFavorites(leagueId);
    }
}
