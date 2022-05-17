package com.example.back.controllers;

import com.example.back.controllers.dto.MatchDto;
import com.example.back.service.MatchService;
import lombok.RequiredArgsConstructor;
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
    public String addMatchToFavorites(@PathVariable Long matchID) {
        return matchService.addMatchToFavorites(matchID);
    }

    @PostMapping(path = "/{matchID}/favorites-remove")
    public String removeMatchFromFavorites(@PathVariable Long matchID) {
        return matchService.removeMatchFromFavorites(matchID);
    }

}
