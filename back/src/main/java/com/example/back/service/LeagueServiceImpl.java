package com.example.back.service;

import com.example.back.controllers.dto.LeagueDto;
import com.example.back.handlers.*;
import com.example.back.models.entities.League;
import com.example.back.models.entities.User;
import com.example.back.repositories.LeagueRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepo leagueRepo;
    private final UserRepo userRepo;

    @Override
    public ResponseEntity<String> addLeagueToFavorites(Long leagueId) {
        League league = leagueRepo.findById(leagueId).orElseThrow(() -> {
            throw new LeagueNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if(user.getFavoriteLeagues().contains(league)) {
                throw new LeagueInFavoritesException();
            }

            user.getFavoriteLeagues().add(league);
            userRepo.save(user);
            return ResponseEntity.ok("League " + league.getName() + " has been added to favorites!");

        }
        throw new NotLoggedInException();
    }

    @Override
    public ResponseEntity<String> removeLeagueFromFavorites(Long leagueId) {
        League league = leagueRepo.findById(leagueId).orElseThrow(() -> {
            throw new LeagueNotFoundException();
        });

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if(user.getFavoriteLeagues().contains(league)) {
                user.getFavoriteLeagues().remove(league);
                userRepo.save(user);
                return ResponseEntity.ok("League " + league.getName() + " removed from favorites!");
            }

            throw new LeagueNotInFavoritesException();
        }
        throw new NotLoggedInException();
    }

    public ArrayList<LeagueDto> getFavoriteLeagues() {
        ArrayList<LeagueDto> result = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long userId = ((User) principal).getId();
            User user = userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            for (League league : user.getFavoriteLeagues()) {
                result.add(new LeagueDto(league.getName(), league.getId(), true));
            }
            return result;
        }
        throw new NotLoggedInException();
    }
}
