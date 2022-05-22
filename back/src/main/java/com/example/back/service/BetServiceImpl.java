package com.example.back.service;

import com.example.back.controllers.dto.BetDto;
import com.example.back.controllers.dto.LeagueDto;
import com.example.back.controllers.dto.TeamDto;
import com.example.back.handlers.NotLoggedInException;
import com.example.back.handlers.UserNotFoundException;
import com.example.back.models.entities.Bet;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.User;
import com.example.back.repositories.BetRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BetServiceImpl implements BetService {

    private final UserRepo userRepo;
    private final MatchService matchService;
    private final BetRepo betRepo;

    @Override
    public ArrayList<BetDto> getBetHistory() {
        ArrayList<BetDto> result = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            for (Bet bet : user.getBets()) {
                if (Objects.equals(bet.getStatus(), "history")) {
                    MatchEntity match = bet.getMatch();
                    boolean isFavorite = user.getFavoriteTeams().contains(match.getTeam1());
                    TeamDto team1 = new TeamDto(match.getTeam1().getName(), match.getTeam1().getId(), isFavorite);
                    isFavorite = user.getFavoriteTeams().contains(match.getTeam2());
                    TeamDto team2 = new TeamDto(match.getTeam2().getName(), match.getTeam2().getId(), isFavorite);
                    isFavorite = user.getFavoriteLeagues().contains(match.getLeague());
                    LeagueDto leagueDto = new LeagueDto(match.getLeague().getName(), match.getLeague().getId(), isFavorite);

                    result.add(new BetDto(bet.getId(), match.getStartTime().toString(), team1, team2, match.getResult(),
                            match.getLeague().getSport().getName(), leagueDto, bet.getAmount(), bet.getStatus(), bet.getResult()));
                }
            }
            return result;
        }
        throw new NotLoggedInException();
    }
}
