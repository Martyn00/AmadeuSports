package com.example.back.service;

import com.example.back.controllers.dto.LeagueDto;
import com.example.back.controllers.dto.MatchDto;
import com.example.back.handlers.*;
import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.User;
import com.example.back.repositories.LeagueRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepo leagueRepo;
    private final UserRepo userRepo;
    private final MatchService matchService;
    private final UserService userService;

    @Override
    public ResponseEntity<String> addLeagueToFavorites(Long leagueId) {
        League league = leagueRepo.findById(leagueId).orElseThrow(() -> {
            throw new LeagueNotFoundException();
        });

        User user = userService.getCurrentUserInstance();

        if(user.getFavoriteLeagues().contains(league)) {
            throw new LeagueInFavoritesException();
        }

        user.getFavoriteLeagues().add(league);
        userRepo.save(user);
        return ResponseEntity.ok("League " + league.getName() + " has been added to favorites!");
    }

    @Override
    public ResponseEntity<String> removeLeagueFromFavorites(Long leagueId) {
        League league = leagueRepo.findById(leagueId).orElseThrow(() -> {
            throw new LeagueNotFoundException();
        });

        User user = userService.getCurrentUserInstance();

        if(user.getFavoriteLeagues().contains(league)) {
            user.getFavoriteLeagues().remove(league);
            userRepo.save(user);
            return ResponseEntity.ok("League " + league.getName() + " removed from favorites!");
        }

        throw new LeagueNotInFavoritesException();
    }

    public ArrayList<LeagueDto> getFavoriteLeagues() {
        ArrayList<LeagueDto> result = new ArrayList<>();

        User user = userService.getCurrentUserInstance();

        for (League league : user.getFavoriteLeagues()) {
            result.add(new LeagueDto(league.getName(), league.getId(), true));
        }

        return result;
    }

    public ArrayList<MatchDto> getUpcomingMatchesByLeagueId(Long leagueId) {
        matchService.updateAllMatches();

        League league = leagueRepo.findById(leagueId).orElseThrow(() -> {
            throw new LeagueNotFoundException();
        });

        ArrayList<MatchDto> result = new ArrayList<>();
        for (MatchEntity match : league.getMatches()) {
            if (!Objects.equals(match.getStatus(), "finished")) {
                matchService.updateMatch(match.getId());
                result.add(matchService.mapToMatchDto(match));
            }
        }

        matchService.sortAscendingByDate(result);
        return result;
    }

    public ArrayList<MatchDto> getPastMatchesByLeagueId(Long leagueId) {
        matchService.updateAllMatches();

        League league = leagueRepo.findById(leagueId).orElseThrow(() -> {
            throw new LeagueNotFoundException();
        });

        ArrayList<MatchDto> result = new ArrayList<>();
        for (MatchEntity match : league.getMatches()) {
            if (Objects.equals(match.getStatus(), "finished")) {
                matchService.updateMatch(match.getId());
                result.add(matchService.mapToMatchDto(match));
            }
        }

        matchService.sortDescendingByDate(result);
        return result;
    }
}
