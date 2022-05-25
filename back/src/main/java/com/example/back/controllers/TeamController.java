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

    @GetMapping(path = "/{id}/matches")
    public List<MatchDto> getMatchesHistory(@PathVariable Long id) {
        return teamService.getMatchesHistory(id);
    }

    @PostMapping(path = "/{teamId}/favorites-add")
    public ResponseEntity<String> addTeamToFavorites(@PathVariable Long teamId) {
        return teamService.addTeamToFavorites(teamId);
    }

    @PostMapping(path = "/{teamId}/favorites-remove")
    public ResponseEntity<String> removeTeamFromFavorites(@PathVariable Long teamId) {
        return teamService.removeTeamFromFavorites(teamId);
    }

    @GetMapping(path = "{teamName}/getTeamByName")
    public ResponseEntity<TeamDto> getTeamByName(@PathVariable String teamName) {
        return teamService.getTeamByName(teamName);
      
    @GetMapping(path = "/favorites")
    public ResponseEntity<List<TeamDto>> removeMatchFromFavorites() {
        return new ResponseEntity<>(teamService.getFavoriteTeams(), HttpStatus.OK);
    }
}
