package com.example.back.service;

import com.example.back.controllers.dto.LeagueDto;
import com.example.back.controllers.dto.MatchDto;
import com.example.back.handlers.*;
import com.example.back.models.entities.League;
import com.example.back.controllers.dto.TeamDto;
import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.models.entities.User;
import com.example.back.models.entities.User;
import com.example.back.repositories.MatchRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepo matchRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;

    public List<MatchDto> getFavoriteMatches() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            return user.getFavoriteMatches().stream().map(this::mapToMatchDto).collect(Collectors.toList());
        }
        throw new NotLoggedInException();
    }

    @Override
    public List<MatchDto> getMatchByDate(Integer numberOfDays) {

        LocalDateTime dateTime = LocalDateTime.now().plusDays(numberOfDays);

        List<MatchEntity> matches = new LinkedList<>();
        for (MatchEntity match : matchRepo.findAll()) {
            if (match.getStartTime().getYear() == dateTime.getYear() &&
                    match.getStartTime().getMonth() == dateTime.getMonth() &&
                    match.getStartTime().getDayOfMonth() == dateTime.getDayOfMonth()) {
                matches.add(match);
            }
        }

        return matches.stream()
                .map(this::mapToMatchDto).collect(Collectors.toList());
    }

    private String getWinner(MatchEntity match) {
        if (!match.isUpcoming()) {
            String[] arrOfGoals = match.getResult().split("-");
            if (Integer.parseInt(arrOfGoals[0]) > Integer.parseInt(arrOfGoals[1])) {
                return match.getTeam1().getName();
            }
            if (Integer.parseInt(arrOfGoals[0]) < Integer.parseInt(arrOfGoals[1])) {
                return match.getTeam2().getName();
            }

            return "Draw";
        }

        return "Upcoming";
    }

    @Override
    public ResponseEntity<String> addMatchToFavorites(Long matchID) {
        MatchEntity match = matchRepo.findById(matchID).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if(user.getFavoriteMatches().contains(match)) {
                throw new MatchAlreadyExistsInFavoritesException();
            }

            user.getFavoriteMatches().add(match);
            userRepo.save(user);
            return ResponseEntity.ok("Match has been added to favorites!");

        }
        throw new NotLoggedInException();
    }

    @Override
    public ResponseEntity<String> removeMatchFromFavorites(Long matchID) {
        MatchEntity match = matchRepo.findById(matchID).orElseThrow(() -> {
            throw new LeagueNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if(user.getFavoriteMatches().contains(match)) {
                user.getFavoriteMatches().remove(match);
                userRepo.save(user);
                return ResponseEntity.ok("Match removed from favorites!");
            }

            throw new MatchNotInFavoritesException();
        }
        throw new NotLoggedInException();
    }

    public MatchDto mapToMatchDto(MatchEntity matchEntity) {
        MatchDto matchDto = modelMapper.map(matchEntity, MatchDto.class);
        matchDto.setLeague(createLeagueDto(matchEntity.getLeague()));
        matchDto.setId(matchEntity.getId());
        matchDto.setCountry(matchEntity.getTeam1().getCountry());
        createTeamDto(matchEntity.getTeam1());
        matchDto.setTeam1(createTeamDto(matchEntity.getTeam1()));
        matchDto.setTeam2(createTeamDto(matchEntity.getTeam2()));
        matchDto.setDetails(matchEntity.getData());
        matchDto.setScore(matchEntity.getResult());
        Boolean isMatchFavorite = getUserFromContext().getFavoriteMatches().contains(matchEntity);
        matchDto.setIsFavorite(isMatchFavorite);
        matchDto.setSport("football");
        matchDto.setWinner(getWinner(matchEntity));

        matchDto.setTime(matchEntity.getStartTime().getHour() + ":" + matchEntity.getStartTime().getMinute());
        return matchDto;
    }

    protected TeamDto createTeamDto(Team team1) {
        TeamDto teamDto = new TeamDto();
        teamDto.setId(team1.getId());
        teamDto.setName(team1.getName());
        Boolean isTeamFavorite = getUserFromContext().getFavoriteTeams().contains(team1);
        teamDto.setIsFavorite(isTeamFavorite);
        return teamDto;
    }

    protected LeagueDto createLeagueDto(League league) {
        LeagueDto leagueDto = new LeagueDto();
        leagueDto.setId(league.getId());
        leagueDto.setName(league.getName());
        Boolean isLeagueFavorite = getUserFromContext().getFavoriteLeagues().contains(league);
        leagueDto.setIsFavorite(isLeagueFavorite);
        return leagueDto;
    }

    User getUserFromContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Optional<User> user = userRepo.findById(((User) principal).getId());
            return user.get();
        } else {
            return null;
        }
    }
}
