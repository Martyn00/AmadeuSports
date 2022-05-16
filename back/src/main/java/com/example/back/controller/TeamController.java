package com.example.back.controller;

import com.example.back.models.entities.MatchEntity;
import com.example.back.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/AmadeusSports/team")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TeamController {

    private final TeamService teamService;

    @GetMapping(path = "/{id}/matches")
    public List<MatchEntity> getMatchesHistory(@PathVariable Long id) {
        return teamService.getMatchesHistory(id);
    }

}
