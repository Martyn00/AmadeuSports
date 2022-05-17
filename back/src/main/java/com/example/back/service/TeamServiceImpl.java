package com.example.back.service;

import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.models.entities.User;
import com.example.back.repositories.TeamRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public String addTeamToFavorites(Long teamId) {
        Optional<Team> team = teamRepo.findById(teamId);

        if(team.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal instanceof UserDetails) {
                Optional<User> user = userRepo.findById(((User) principal).getId());

                if(user.isPresent()) {
                    if(!user.get().getFavoriteTeams().contains(team.get())) {
                        user.get().getFavoriteTeams().add(team.get());
                        userRepo.save(user.get());
                        return "The team " + teamId + " has been added as favorites to user " + user.get().getUsername();
                    }
                    return "The team is already at favorites";
                }
                return "The user does not exist";
            }
            return "You are not logged in";
        }
        return "The team does not exist";
    }

    @Override
    public String removeTeamFromFavorites(Long teamId) {
        Optional<Team> team = teamRepo.findById(teamId);

        if(team.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal instanceof UserDetails) {
                Optional<User> user = userRepo.findById(((User) principal).getId());

                if(user.isPresent()) {
                    if(user.get().getFavoriteTeams().contains(team.get())) {
                        user.get().getFavoriteTeams().remove(team.get());
                        userRepo.save(user.get());
                        return "The team " + teamId + " has been removed from favorites from user " + user.get().getUsername();
                    }
                    return "The team is not at favorites";
                }
                return "The user does not exist";
            }
            return "You are not logged in";
        }
        return "The team does not exist";
    }
}
