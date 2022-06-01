package com.example.back.service;

import com.example.back.controllers.dto.*;
import com.example.back.handlers.*;
import com.example.back.models.entities.*;
import com.example.back.repositories.BetRepo;
import com.example.back.repositories.MatchRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class BetServiceImpl implements BetService {

    private final UserRepo userRepo;
    private final MatchService matchService;
    private final MatchRepo matchRepo;
    private final BetRepo betRepo;
    private final UserService userService;

    @Override
    public ResponseEntity<ArrayList<BetDto>> getBetHistory() {
        return getBetsByStatus("history");
    }

    @Override
    public ResponseEntity<ArrayList<BetDto>> getPendingBets() {
        return getBetsByStatus("pending");
    }

    public ResponseEntity<ArrayList<BetDto>> getCurrentBets() {
        return getBetsByStatus("current");
    }

    public ResponseEntity<String> addBet(AddBetDto addBetDto) {
        updateAllBets();

        MatchEntity match = matchRepo.findById(addBetDto.getMatchId()).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        User user2 = userRepo.findByUsername(addBetDto.getUsername()).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        User user1 = userService.getCurrentUserInstance();

        if (user1 == user2) {
            throw new BetWithYourselfException();
        }

        matchService.updateMatch(addBetDto.getMatchId());
        if (!Objects.equals(match.getStatus(), "upcoming")) {
            throw new OutgoingMatchBetException();
        }

        for (Bet bet : user1.getBets()) {
            if (Objects.equals(bet.getMatch().getId(), addBetDto.getMatchId()) && bet.getUser2() == user2) {
                throw new BetDuplicateException();
            }
        }

        if (user1.getWallet() - addBetDto.getAmount() < 0) {
            throw new NotEnoughMoneyException();
        }

        Bet bet = new Bet(match, user1, user2, addBetDto.getBetType(), BetType.PENDING, addBetDto.getAmount(), "pending", -1);
        betRepo.save(bet);

        user1.getBets().add(bet);
        user1.setWallet(user1.getWallet() - addBetDto.getAmount());
        user2.getBets().add(bet);
        userRepo.save(user1);
        userRepo.save(user2);

        return ResponseEntity.ok("You placed a bet for this match with user " + user2.getUsername() + ".");

    }

    public ResponseEntity<String> acceptBet(Long betId, BetType betType) {
        updateAllBets();

        Bet bet = betRepo.findById(betId).orElseThrow(() -> {
            throw new BetNotFoundException();
        });

        if (!(Objects.equals(bet.getStatus(), "pending"))) {
            throw new AcceptNonPendingBetException();
        }

        User user = userService.getCurrentUserInstance();

        if (betType == bet.getBetChoiceUser1()) {
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

        bet.setBetChoiceUser2(betType);

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(bet.getMatch().getStartTime())) {
            throw new AcceptBetAfterMatchStartsException();
        } else {
            bet.setStatus("current");
        }

        betRepo.save(bet);

        user.setWallet(user.getWallet() - bet.getAmount());
        userRepo.save(user);

        return ResponseEntity.ok("You accepted a bet for this match with user " + bet.getUser1().getUsername() + ".");
    }

    public ResponseEntity<String> cancelBet(Long betId) {
        updateAllBets();

        Bet bet = betRepo.findById(betId).orElseThrow(() -> {
            throw new BetNotFoundException();
        });

        if (!(Objects.equals(bet.getStatus(), "pending"))) {
            throw new CancelNonPendingBetException();
        }

        User user = userService.getCurrentUserInstance();

        if (user != bet.getUser1() && user != bet.getUser2()) {
            throw new NotUsersBetException();
        }

        bet.getUser1().setWallet(bet.getUser1().getWallet() + bet.getAmount());

        bet.getUser1().getBets().remove(bet);
        bet.getUser2().getBets().remove(bet);
        betRepo.delete(bet);
        userRepo.save(bet.getUser1());
        userRepo.save(bet.getUser2());

        return ResponseEntity.ok("You canceled this bet.");
    }

    private ResponseEntity<ArrayList<BetDto>> getBetsByStatus(String status) {
        updateAllBets();

        ArrayList<BetDto> result = new ArrayList<>();

        User user = userService.getCurrentUserInstance();

        for (Bet bet : user.getBets()) {
            if (Objects.equals(bet.getStatus(), status)) {
                result.add(betToBetDtoMapping(bet, user, status));
            }
        }

        if (Objects.equals(status, "pending") || Objects.equals(status, "current")) {
            return ResponseEntity.ok(sortBetsByDateAscending(result));
        }

        return ResponseEntity.ok(sortBetsByDateDescending(result));
    }

    private void updateAllBets() {
        for (Bet bet : betRepo.findAll()) {
            updateBet(bet);
        }
    }

    private void updateBet(Bet bet) {
        matchService.updateMatch(bet.getMatch().getId());

        if (Objects.equals(bet.getMatch().getStatus(), "going")) {
            if (deleteIfPendingBet(bet)) {
                return;
            }
            bet.setStatus("current");
            betRepo.save(bet);
        }

        if (Objects.equals(bet.getMatch().getStatus(), "finished")) {
            if (deleteIfPendingBet(bet)) {
                return;
            }
            if (Objects.equals(bet.getStatus(), "current")) {
                bet.setStatus("history");
            } else {
                return;
            }

            betFinished(bet);
            betRepo.save(bet);
        }
    }

    private void betFinished(Bet bet) {
        List<String> goalsStr = new ArrayList<>(Arrays.asList(bet.getMatch().getResult().split("-")));
        List<Integer> goals = new ArrayList<>();
        goals.add(Integer.parseInt(goalsStr.get(0)));
        goals.add(Integer.parseInt(goalsStr.get(1)));

        if (goals.get(0) > goals.get(1)) {
            bet.setResult(0);
            updateWallet(BetType.HOME, bet);
        }
        if (Objects.equals(goals.get(0), goals.get(1))) {
            bet.setResult(1);
            updateWallet(BetType.EQUAL, bet);
        }
        if (goals.get(0) < goals.get(1)) {
            bet.setResult(2);
            updateWallet(BetType.AWAY, bet);
        }
    }

    private void updateWallet(BetType betType, Bet bet) {
        User user1 = bet.getUser1();
        User user2 = bet.getUser2();
        if (bet.getBetChoiceUser1() == betType) {
            user1.setWallet(user1.getWallet() + 2 * bet.getAmount());
            userRepo.save(user1);
            return;
        }
        if (bet.getBetChoiceUser2() == betType) {
            user2.setWallet(user2.getWallet() + 2 * bet.getAmount());
            userRepo.save(user2);
            return;
        }
        user1.setWallet(user1.getWallet() + bet.getAmount());
        user2.setWallet(user2.getWallet() + bet.getAmount());
        userRepo.save(user1);
        userRepo.save(user2);
    }

    private boolean deleteIfPendingBet(Bet bet) {
        if (Objects.equals(bet.getStatus(), "pending")) {
            bet.getUser1().setWallet(bet.getUser1().getWallet() + bet.getAmount());
            userRepo.save(bet.getUser1());
            betRepo.delete(bet);
            return true;
        }
        return false;
    }

    private ArrayList<BetDto> sortBetsByDateAscending(ArrayList<BetDto> result) {
        result.sort(Comparator.comparing(BetDto::getTime));

        return result;
    }

    private ArrayList<BetDto> sortBetsByDateDescending(ArrayList<BetDto> result) {
        result.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));

        return result;
    }

    private BetDto betToBetDtoMapping(Bet bet, User user, String status) {
        MatchEntity match = bet.getMatch();
        boolean isFavorite = user.getFavoriteTeams().contains(match.getTeam1());
        TeamDto team1 = new TeamDto(match.getTeam1().getName(), match.getTeam1().getId(), isFavorite);
        isFavorite = user.getFavoriteTeams().contains(match.getTeam2());
        TeamDto team2 = new TeamDto(match.getTeam2().getName(), match.getTeam2().getId(), isFavorite);
        isFavorite = user.getFavoriteLeagues().contains(match.getLeague());
        LeagueDto leagueDto = new LeagueDto(match.getLeague().getName(), match.getLeague().getId(), isFavorite);

        UserBetChoiceDto userBetChoiceDto = new UserBetChoiceDto(bet.getUser1().getId(), bet.getUser2().getId(),
                bet.getBetChoiceUser1().ordinal(), bet.getBetChoiceUser2().ordinal());

        if (Objects.equals(status, "pending") && Objects.equals(user.getId(), bet.getUser1().getId())) {
            return new BetDto(bet.getId(), match.getStartTime().toString(), team1, team2, match.getResult(),
                    match.getLeague().getSport().getName(), leagueDto, bet.getAmount(), "toBeAccepted", bet.getResult(), userBetChoiceDto);
        } else {
            return new BetDto(bet.getId(), match.getStartTime().toString(), team1, team2, match.getResult(),
                    match.getLeague().getSport().getName(), leagueDto, bet.getAmount(), bet.getStatus(), bet.getResult(), userBetChoiceDto);
        }
    }
}
