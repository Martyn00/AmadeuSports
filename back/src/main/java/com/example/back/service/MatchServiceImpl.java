package com.example.back.service;

import com.example.back.controllers.dto.*;
import com.example.back.handlers.*;
import com.example.back.models.Role;
import com.example.back.models.entities.*;
import com.example.back.models.entities.League;
import com.example.back.models.entities.User;
import com.example.back.repositories.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepo matchRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final TeamRepo teamRepo;
    private final LeagueRepo leagueRepo;
    private final MatchEventRepo matchEventRepo;
    private final UserService userService;
    private final int footballDuration = 90;

    public ResponseEntity<List<MatchDto>> getFavoriteMatches() {
        updateAllMatches();

        User user = userService.getCurrentUserInstance();
        List<MatchDto> matches = new ArrayList<>();
        for (MatchEntity match : user.getFavoriteMatches()) {
            matches.add(mapToMatchDto(match));
        }

        return ResponseEntity.ok(matches);
    }

    @Override
    public ResponseEntity<List<MatchDto>> getMatchByDate(Integer numberOfDays) {
        updateAllMatches();

        LocalDateTime dateTime = LocalDateTime.now().plusDays(numberOfDays);

        List<MatchDto> matches = new LinkedList<>();
        for (MatchEntity match : matchRepo.findAll()) {
            if (match.getStartTime().getYear() == dateTime.getYear() &&
                    match.getStartTime().getMonth() == dateTime.getMonth() &&
                    match.getStartTime().getDayOfMonth() == dateTime.getDayOfMonth()) {
                matches.add(mapToMatchDto(match));
            }
        }

        return ResponseEntity.ok(matches);
    }

    private String getWinner(MatchEntity match) {
        if (Objects.equals(match.getStatus(), "finished")) {
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
    public ResponseEntity addMatchToFavorites(Long matchID) {
        updateAllMatches();

        MatchEntity match = matchRepo.findById(matchID).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        User user = userService.getCurrentUserInstance();

        if(user.getFavoriteMatches().contains(match)) {
            throw new MatchAlreadyExistsInFavoritesException();
        }

        user.getFavoriteMatches().add(match);
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity removeMatchFromFavorites(Long matchID) {
        updateAllMatches();

        MatchEntity match = matchRepo.findById(matchID).orElseThrow(() -> {
            throw new LeagueNotFoundException();
        });

        User user = userService.getCurrentUserInstance();

        if(user.getFavoriteMatches().contains(match)) {
            user.getFavoriteMatches().remove(match);
            userRepo.save(user);
            return ResponseEntity.ok().build();
        }

        throw new MatchNotInFavoritesException();
    }

    public ResponseEntity addMatch(AddMatchDto addMatchDto) {
        updateAllMatches();

        User user = userService.getCurrentUserInstance();

        if (user.getRole() != Role.ADMIN) {
            throw new AdminPrivilegiesException();
        }

        Team team1 = teamRepo.findByName(addMatchDto.getTeam1Name()).orElseThrow(() -> {
            throw new TeamNotFoundException();
        });
        Team team2 = teamRepo.findByName(addMatchDto.getTeam2Name()).orElseThrow(() -> {
            throw new TeamNotFoundException();
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(addMatchDto.getStartTime(), formatter);

        League league;
        if (team1.getHomeLeague() == team2.getHomeLeague()) {
            league = team1.getHomeLeague();
        } else {
            league = leagueRepo.findByName("Liga Campionilor").orElseThrow(() -> {
                throw new LeagueNotFoundException();
            });
        }

        int duration = 10;
        if (Objects.equals(league.getSport().getName(), "Football")) {
            duration = footballDuration;
        }

        LocalDateTime now = LocalDateTime.now();
        String status;
        if (startDate.plusMinutes(duration).isBefore(now)) {
            status = "finished";
        } else if (startDate.isBefore(now)) {
            status = "going";
        } else {
            status = "upcoming";
        }

        MatchEntity match = new MatchEntity(team1, team2, league, null, status, addMatchDto.getScore(), startDate, null, duration);
        matchRepo.save(match);
        team1.getMatches().add(match);
        team2.getMatches().add(match);
        teamRepo.save(team1);
        teamRepo.save(team2);

        return ResponseEntity.ok("A new match has been added!");
    }

    public ResponseEntity addEvent(AddEventDto addEventDto) {
        updateAllMatches();

        MatchEntity match = matchRepo.findById(addEventDto.getMatchId()).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        User user = userService.getCurrentUserInstance();

        if (user.getRole() != Role.ADMIN) {
            throw new AdminPrivilegiesException();
        }

        if (addEventDto.getMinute() <= 0 || addEventDto.getMinute() > match.getDuration()) {
            throw new WrongNumOfMinException();
        }

        MatchEvent event = new MatchEvent(addEventDto.getGoal(), addEventDto.getMinute());
        event.setUpdated(false);
        matchEventRepo.save(event);
        match.getEvents().add(event);
        matchRepo.save(match);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<MatchDto> updateMatch(Long matchId) {
        MatchEntity match = matchRepo.findById(matchId).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        LocalDateTime now = LocalDateTime.now();
        if (match.getStartTime().isBefore(now)) {
            if (Objects.equals(match.getResult(), "-")) {
                match.setResult("0-0");
            }

            List<String> goalsStr = new ArrayList<>(Arrays.asList(match.getResult().split("-")));
            List<Integer> goals = new ArrayList<>();
            goals.add(Integer.parseInt(goalsStr.get(0)));
            goals.add(Integer.parseInt(goalsStr.get(1)));

            int minutesPlayed = now.getMinute() - match.getStartTime().getMinute();
            for (MatchEvent event : match.getEvents()) {
                if (minutesPlayed >= event.getMin() && !event.isUpdated()) {
                    goals.set(event.getGoal().ordinal(), goals.get(event.getGoal().ordinal()) + 1);
                    event.setUpdated(true);
                    matchEventRepo.save(event);
                }
            }

            if (match.getStartTime().plusMinutes(match.getDuration()).isAfter(now)) {
                match.setStatus("going");
            } else {
                match.setStatus("finished");
            }
            match.setResult(goals.get(0) + "-" + goals.get(1));
            matchRepo.save(match);
        }

        return ResponseEntity.ok(mapToMatchDto(match));
    }

    public void updateAllMatches() {
        for (MatchEntity match : matchRepo.findAll()) {
            updateMatch(match.getId());
        }
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
        matchDto.setIsFavorite(isFavorite(matchEntity, null, null));
        matchDto.setSport("football");
        matchDto.setWinner(getWinner(matchEntity));
        matchDto.setTime(matchEntity.getStartTime().toString());
        return matchDto;
    }

    protected TeamDto createTeamDto(Team team1) {
        TeamDto teamDto = new TeamDto();
        teamDto.setId(team1.getId());
        teamDto.setName(team1.getName());
        teamDto.setIsFavorite(isFavorite(null, team1, null));
        return teamDto;
    }

    protected LeagueDto createLeagueDto(League league) {
        LeagueDto leagueDto = new LeagueDto();
        leagueDto.setId(league.getId());
        leagueDto.setName(league.getName());
        leagueDto.setIsFavorite(isFavorite(null, null, league));
        return leagueDto;
    }
    private Boolean isFavorite(MatchEntity match, Team team, League league) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });
            if (match != null) {
                return user.getFavoriteMatches().contains(match);
            }
            if (team != null) {
                return user.getFavoriteTeams().contains(team);
            }
            if (league != null) {
                return user.getFavoriteLeagues().contains(league);
            }
        }
        return false;
    }


    public void sortAscendingByDate(ArrayList<MatchDto> result) {
        result.sort(Comparator.comparing(MatchDto::getTime));
    }
    public void sortDescendingByDate(ArrayList<MatchDto> result) {
        result.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
    }
}
