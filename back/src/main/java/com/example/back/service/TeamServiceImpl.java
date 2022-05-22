package com.example.back.service;

import com.example.back.controllers.dto.TeamDto;
import com.example.back.handlers.*;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.models.entities.User;
import com.example.back.repositories.TeamRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepo teamRepo;
    private final UserRepo userRepo;

    @Override
    public List<MatchEntity> getMatchesHistory(Long id) {
        if (teamRepo.findById(id).isPresent()) {
            return new ArrayList<>(teamRepo.findById(id).get().getMatches());
        }
        return null;
    }

    @Override
    public ResponseEntity<String> addTeamToFavorites(Long teamId) {
        Team team = teamRepo.findById(teamId).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if(user.getFavoriteTeams().contains(team)) {
                throw new TeamInFavoritesException();
            }

            user.getFavoriteTeams().add(team);
            userRepo.save(user);
            return ResponseEntity.ok("Team " + team.getName() + " has been added to favorites!");

        }
        throw new NotLoggedInException();
    }

    @Override
    public ResponseEntity<String> removeTeamFromFavorites(Long teamId) {
        Team team = teamRepo.findById(teamId).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if(user.getFavoriteTeams().contains(team)) {
                user.getFavoriteTeams().remove(team);
                userRepo.save(user);
                return ResponseEntity.ok("Team " + team.getName() + " removed from favorites!");
            }

            throw new TeamNotInFavoritesException();
        }
        throw new NotLoggedInException();
    }

    @Override
    public List<TeamDto> getFavoriteTeams() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            return user.getFavoriteTeams().stream().map(this::mapTeamTOTeamDtoFavorite).collect(Collectors.toList());

        }
        throw new NotLoggedInException();
    }

    private TeamDto mapTeamTOTeamDtoFavorite(Team team){
        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setIsFavorite(true);
        teamDto.setId(team.getId());
        return teamDto;
    }
}
