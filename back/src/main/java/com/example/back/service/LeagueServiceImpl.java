package com.example.back.service;

import com.example.back.models.entities.League;
import com.example.back.models.entities.User;
import com.example.back.repositories.LeagueRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepo leagueRepo;
    private final UserRepo userRepo;

    @Override
    public String addLeagueToFavorites(Long leagueId) {
        Optional<League> league = leagueRepo.findById(leagueId);

        if(league.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal instanceof UserDetails) {
                Optional<User> user = userRepo.findById(((User) principal).getId());

                if(user.isPresent()) {
                    if(!user.get().getFavoriteLeagues().contains(league.get())) {
                        user.get().getFavoriteLeagues().add(league.get());
                        userRepo.save(user.get());
                        return "The league " + leagueId + " has been added as favorites to user " + user.get().getUsername();
                    }
                    return "The league is already at favorites";
                }
                return "The user does not exist";
            }
            return "You are not logged in";
        }
        return "The league does not exist";
    }

    @Override
    public String removeLeagueFromFavorites(Long leagueId) {
        Optional<League> league = leagueRepo.findById(leagueId);

        if(league.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal instanceof UserDetails) {
                Optional<User> user = userRepo.findById(((User) principal).getId());

                if(user.isPresent()) {
                    if(user.get().getFavoriteLeagues().contains(league.get())) {
                        user.get().getFavoriteLeagues().remove(league.get());
                        userRepo.save(user.get());
                        return "The league " + leagueId + " has been removed from favorites from user " + user.get().getUsername();
                    }
                    return "The league is not at favorites";
                }
                return "The user does not exist";
            }
            return "You are not logged in";
        }
        return "The league does not exist";
    }
}
