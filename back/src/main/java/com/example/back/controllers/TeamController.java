package com.example.back.controllers;

import com.example.back.controllers.dto.MatchDto;
import com.example.back.controllers.dto.TeamDto;
import com.example.back.models.entities.MatchEntity;
import com.example.back.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/AmadeusSports/team")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TeamController {

    private final TeamService teamService;

    @GetMapping(path = "/matches-history/{id}")
    public ResponseEntity<List<MatchDto>> getMatchesHistory(@PathVariable Long id) {
        return teamService.getMatchesHistory(id);
    }

    @GetMapping(path = "/matches-upcoming/{id}")
    public ResponseEntity<List<MatchDto>> getUpcomingMatches(@PathVariable Long id) {
        return teamService.getUpcomingMatches(id);
    }

    @PostMapping(path = "/favorites-add/{teamId}")
    public ResponseEntity<String> addTeamToFavorites(@PathVariable Long teamId) {
        return teamService.addTeamToFavorites(teamId);
    }

    @PostMapping(path = "/favorites-remove/{teamId}")
    public ResponseEntity<String> removeTeamFromFavorites(@PathVariable Long teamId) {
        return teamService.removeTeamFromFavorites(teamId);
    }

    @GetMapping(path = "/getTeamByName/{teamName}")
    public ResponseEntity<TeamDto> getTeamByName(@PathVariable String teamName) {
        return teamService.getTeamByName(teamName);
    }
}
