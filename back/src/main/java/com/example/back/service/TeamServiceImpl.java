package com.example.back.service;

import com.example.back.controllers.dto.MatchDto;
import com.example.back.controllers.dto.TeamDto;
import com.example.back.handlers.*;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.models.entities.User;
import com.example.back.repositories.TeamRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepo teamRepo;
    private final UserRepo userRepo;
    private final MatchService matchService;
    private final UserService userService;

    @Override
    public ResponseEntity<List<MatchDto>> getMatchesHistory(Long id) {
        matchService.updateAllMatches();

        Team team = teamRepo.findById(id).orElseThrow(() -> {
            throw new TeamNotFoundException();
        });

        ArrayList<MatchDto> result = new ArrayList<>();
        for (MatchEntity match : team.getMatches()) {
            if (Objects.equals(match.getStatus(), "finished")) {
                result.add(matchService.mapToMatchDto(match));
            }
        }

        matchService.sortDescendingByDate(result);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<List<MatchDto>> getUpcomingMatches(Long id) {
        matchService.updateAllMatches();

        Team team = teamRepo.findById(id).orElseThrow(() -> {
            throw new TeamNotFoundException();
        });

        ArrayList<MatchDto> result = new ArrayList<>();
        for (MatchEntity match : team.getMatches()) {
            if (!Objects.equals(match.getStatus(), "finished")) {
                result.add(matchService.mapToMatchDto(match));
            }
        }

        matchService.sortAscendingByDate(result);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<String> addTeamToFavorites(Long teamId) {
        Team team = teamRepo.findById(teamId).orElseThrow(() -> {
            throw new TeamNotFoundException();
        });

        User user = userService.getCurrentUserInstance();

        if(user.getFavoriteTeams().contains(team)) {
            throw new TeamInFavoritesException();
        }

        user.getFavoriteTeams().add(team);
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity removeTeamFromFavorites(Long teamId) {
        Team team = teamRepo.findById(teamId).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        User user = userService.getCurrentUserInstance();

        if(user.getFavoriteTeams().contains(team)) {
            user.getFavoriteTeams().remove(team);
            userRepo.save(user);
            return ResponseEntity.ok().build();
        }

        throw new TeamNotInFavoritesException();
    }

   @Override
    public ResponseEntity<TeamDto> getTeamByName(String teamName) {
        User user = userService.getCurrentUserInstance();

        Team team = teamRepo.findByName(teamName).orElseThrow(() -> {
            throw new TeamNotFoundException();
        });

        TeamDto teamDto = new TeamDto(team.getName(),
                                        team.getId(),
                                        user.getFavoriteTeams().contains(team));

        return ResponseEntity.ok(teamDto);
    }

    @Override
    public List<TeamDto> getFavoriteTeams() {
        User user = userService.getCurrentUserInstance();

        return user.getFavoriteTeams().stream().map(this::mapTeamTOTeamDtoFavorite).collect(Collectors.toList());
    }

    private TeamDto mapTeamTOTeamDtoFavorite(Team team){
        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setIsFavorite(true);
        teamDto.setId(team.getId());
        return teamDto;
    }
}
