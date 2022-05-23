package com.example.back.service;

import com.example.back.controllers.dto.LeagueDto;
import com.example.back.controllers.dto.MatchDto;
import com.example.back.handlers.*;
import com.example.back.models.Role;
import com.example.back.models.entities.*;
import com.example.back.controllers.dto.TeamDto;
import com.example.back.models.entities.League;
import com.example.back.models.entities.User;
import com.example.back.repositories.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepo matchRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final TeamRepo teamRepo;
    private final LeagueRepo leagueRepo;
    private final MatchEventRepo matchEventRepo;

    public List<MatchDto> getFavoriteMatches() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            updateListOfMatches(user.getFavoriteMatches());
            return user.getFavoriteMatches().stream().map(this::mapToMatchDto).collect(Collectors.toList());
        }
        throw new NotLoggedInException();
    }

    @Override
    public List<MatchDto> getMatchByDate(Integer numberOfDays) {

        LocalDateTime dateTime = LocalDateTime.now().plusDays(numberOfDays);

        Set<MatchEntity> matches = new HashSet<>();
        for (MatchEntity match : matchRepo.findAll()) {
            if (match.getStartTime().getYear() == dateTime.getYear() &&
                    match.getStartTime().getMonth() == dateTime.getMonth() &&
                    match.getStartTime().getDayOfMonth() == dateTime.getDayOfMonth()) {
                matches.add(match);
            }
        }

        updateListOfMatches(matches);
        return matches.stream()
                .map(this::mapToMatchDto).collect(Collectors.toList());
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

    public ResponseEntity<String> addMatch(String team1Name, String team2Name, String startTime, String result) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if (user.getRole() != Role.ADMIN) {
                throw new AdminPrivilegiesException();
            }

            Team team1 = teamRepo.findByName(team1Name).orElseThrow(() -> {
                throw new TeamNotFoundException();
            });
            Team team2 = teamRepo.findByName(team2Name).orElseThrow(() -> {
                throw new TeamNotFoundException();
            });

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDate = LocalDateTime.parse(startTime, formatter);

            League league;
            if (team1.getHomeLeague() == team2.getHomeLeague()) {
                league = team1.getHomeLeague();
            } else {
                league = leagueRepo.findByName("Liga Campionilor").orElseThrow(() -> {
                    throw new LeagueNotFoundException();
                });
            }

            LocalDateTime now = LocalDateTime.now();
            String status;
            if (startDate.plusMinutes(90).isBefore(now)) {
                status = "finished";
            } else if (startDate.isBefore(now)) {
                status = "going";
            } else {
                status = "upcoming";
            }

            MatchEntity match = new MatchEntity(team1, team2, league, null, status, result, startDate, null);
            matchRepo.save(match);

            return ResponseEntity.ok("A new match has been added!");

        }
        throw new NotLoggedInException();
    }

    public ResponseEntity<String> addEvent(Long matchId, int goal, int min) {
        MatchEntity match = matchRepo.findById(matchId).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if (user.getRole() != Role.ADMIN) {
                throw new AdminPrivilegiesException();
            }

            if (min < 0 || min > 90) {
                throw new WrongNumOfMinException();
            }
            if (goal != 0 && goal != 1) {
                throw new GoalException();
            }

            MatchEvent event = new MatchEvent(goal, min);
            event.setUpdated(false);
            matchEventRepo.save(event);
            match.getEvents().add(event);
            matchRepo.save(match);

            return ResponseEntity.ok("A new event has been added for this match!");
        }

        throw new NotLoggedInException();
    }
    public MatchDto updateMatch(Long matchId) {
        MatchEntity match = matchRepo.findById(matchId).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if (user.getRole() != Role.ADMIN) {
                throw new AdminPrivilegiesException();
            }

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
                    if (minutesPlayed > event.getMin() && !event.isUpdated()) {
                        goals.set(event.getGoal(), goals.get(event.getGoal()) + 1);
                        event.setUpdated(true);
                        matchEventRepo.save(event);
                    }
                }

                if (match.getStartTime().plusMinutes(90).isAfter(now)) {
                    match.setStatus("going");
                } else {
                    match.setStatus("finished");
                }
                match.setResult(goals.get(0) + "-" + goals.get(1));
                matchRepo.save(match);
            }

            return mapToMatchDto(match);
        }

        throw new NotLoggedInException();
    }

    public void updateListOfMatches(Set<MatchEntity> matches) {
        for (MatchEntity match : matches) {
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
        Boolean isMatchFavorite = getUserFromContext().getFavoriteMatches().contains(matchEntity);
        matchDto.setIsFavorite(isMatchFavorite);
        matchDto.setSport("football");
        matchDto.setWinner(getWinner(matchEntity));
        matchDto.setTime(matchEntity.getStartTime().toString());
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
    public void sortAscendingByDate(ArrayList<MatchDto> result) {
        result.sort(Comparator.comparing(MatchDto::getTime));
    }
    public void sortDescendingByDate(ArrayList<MatchDto> result) {
        result.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
    }

}
