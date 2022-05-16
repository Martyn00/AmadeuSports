package com.example.back.controllers;

import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/AmadeusSports/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}/favorite-matches")
    public List<MatchEntity> getFavoriteMatchesByUserId(@PathVariable Long id) {
        return userService.getFavoriteMatchesByUserId(id);
    }

    @GetMapping(path = "/{id}/favorite-teams")
    public List<Team> getFavoriteTeamsByUserId(@PathVariable Long id) {
        return userService.getFavoriteTeamsByUserId(id);
    }

    @GetMapping(path = "/{id}/favorite-leagues")
    public List<League> getFavoriteLeaguesByUserId(@PathVariable Long id) {
        return userService.getFavoriteLeaguesByUserId(id);
    }
}
