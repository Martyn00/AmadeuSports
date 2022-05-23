package com.example.back.service;

import com.example.back.controllers.dto.BetDto;
import com.example.back.controllers.dto.LeagueDto;
import com.example.back.controllers.dto.TeamDto;
import com.example.back.handlers.*;
import com.example.back.models.entities.*;
import com.example.back.repositories.BetRepo;
import com.example.back.repositories.MatchRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BetServiceImpl implements BetService {

    private final UserRepo userRepo;
    private final MatchService matchService;
    private final MatchRepo matchRepo;
    private final BetRepo betRepo;

    @Override
    public ArrayList<BetDto> getBetHistory() {
        return getBetsByStatus("history");
    }

    @Override
    public ArrayList<BetDto> getPendingBets() {
        return getBetsByStatus("pending");
    }

    public ArrayList<BetDto> getCurrentBets() {
        return getBetsByStatus("current");
    }

    public ResponseEntity<String> addBet(Long matchId, Long userId, int betType, int amount) {
        MatchEntity match = matchRepo.findById(matchId).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        User user2 = userRepo.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof User) {
            Long userId1 = ((User) principal).getId();
            if (Objects.equals(userId1, userId)) {
                throw new BetWithYourselfException();
            }

            User user1 = userRepo.findById(userId1).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            for (Bet bet : user1.getBets()) {
                if (Objects.equals(bet.getMatch().getId(), matchId) && Objects.equals(bet.getUser2().getId(), userId)) {
                    throw new BetDuplicateException();
                }
            }

            if (user1.getWallet() - amount < 0) {
                throw new NotEnoughMoneyException();
            }

            Bet bet = new Bet(match, user1, user2, BetType.values()[betType], BetType.PENDING, amount, "pending", -1);
            betRepo.save(bet);

            user1.getBets().add(bet);
            user1.setWallet(user1.getWallet() - amount);
            user2.getBets().add(bet);
            userRepo.save(user1);
            userRepo.save(user2);

            return ResponseEntity.ok("You placed a bet for this match with user " + user2.getUsername() + ".");

        }
        throw new NotLoggedInException();
    }

    public ResponseEntity<String> acceptBet(Long betId, int betType) {
        Bet bet = betRepo.findById(betId).orElseThrow(() -> {
            throw new BetNotFoundException();
        });

        if (!(Objects.equals(bet.getStatus(), "pending"))) {
            throw new AcceptNonPendingBetException();
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof User) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if (BetType.values()[betType] == bet.getBetChoiceUser1()) {
                throw new SameBetTypeException();
            }

            if (user.getWallet() - bet.getAmount() < 0) {
                throw new NotEnoughMoneyException();
            }

            boolean isInUserBetList = false;
            for (Bet bet1 : user.getBets()) {
                if (Objects.equals(bet1.getUser2().getId(), user.getId())) {
                    isInUserBetList = true;
                    break;
                }
            }
            if (!isInUserBetList) {
                throw new NotInUserBetListException();
            }

            bet.setBetChoiceUser2(BetType.values()[betType]);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime matchFinish = bet.getMatch().getStartTime().plusHours(2);
            if (now.isAfter(matchFinish)) {
                bet.setStatus("history");
            } else {
                bet.setStatus("current");
            }

            betRepo.save(bet);

            user.setWallet(user.getWallet() - bet.getAmount());
            userRepo.save(user);

            return ResponseEntity.ok("You accepted a bet for this match with user " + bet.getUser1().getUsername() + ".");

        }
        throw new NotLoggedInException();
    }

    public ResponseEntity<String> cancelBet(Long betId) {
        Bet bet = betRepo.findById(betId).orElseThrow(() -> {
            throw new BetNotFoundException();
        });

        if (!(Objects.equals(bet.getStatus(), "pending"))) {
            throw new CancelNonPendingBetException();
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof User) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if (user == bet.getUser1()) {
                user.setWallet(user.getWallet() + bet.getAmount());
            } else if (user != bet.getUser2()) {
                throw new NotUsersBetException();
            }


            bet.getUser1().getBets().remove(bet);
            bet.getUser2().getBets().remove(bet);
            betRepo.delete(bet);
            userRepo.save(bet.getUser1());
            userRepo.save(bet.getUser2());

            return ResponseEntity.ok("You canceled this bet.");
        }
        throw new NotLoggedInException();
    }

    private ArrayList<BetDto> getBetsByStatus(String status) {
        ArrayList<BetDto> result = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            for (Bet bet : user.getBets()) {
                if (Objects.equals(bet.getStatus(), status)) {
                    MatchEntity match = bet.getMatch();
                    boolean isFavorite = user.getFavoriteTeams().contains(match.getTeam1());
                    TeamDto team1 = new TeamDto(match.getTeam1().getName(), match.getTeam1().getId(), isFavorite);
                    isFavorite = user.getFavoriteTeams().contains(match.getTeam2());
                    TeamDto team2 = new TeamDto(match.getTeam2().getName(), match.getTeam2().getId(), isFavorite);
                    isFavorite = user.getFavoriteLeagues().contains(match.getLeague());
                    LeagueDto leagueDto = new LeagueDto(match.getLeague().getName(), match.getLeague().getId(), isFavorite);

                    if (Objects.equals(status, "pending") && Objects.equals(user.getId(), bet.getUser1().getId())) {
                        result.add(new BetDto(bet.getId(), match.getStartTime().toString(), team1, team2, match.getResult(),
                                match.getLeague().getSport().getName(), leagueDto, bet.getAmount(), "toBeAccepted", bet.getResult()));
                    } else {
                        result.add(new BetDto(bet.getId(), match.getStartTime().toString(), team1, team2, match.getResult(),
                                match.getLeague().getSport().getName(), leagueDto, bet.getAmount(), bet.getStatus(), bet.getResult()));
                    }
                }
            }
            if (Objects.equals(status, "history")) {
                return sortBetsByDateDescending(result);
            }
            if (Objects.equals(status, "pending")) {
                return sortBetsByDateAscending(result);
            }
            if (Objects.equals(status, "current")) {
                return sortBetsByDateAscending(result);
            }
        }
        throw new NotLoggedInException();
    }

    private ArrayList<BetDto> sortBetsByDateAscending(ArrayList<BetDto> result) {
        result.sort(Comparator.comparing(BetDto::getTime));

        return result;
    }

    private ArrayList<BetDto> sortBetsByDateDescending(ArrayList<BetDto> result) {
        result.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));

        return result;
    }
}
