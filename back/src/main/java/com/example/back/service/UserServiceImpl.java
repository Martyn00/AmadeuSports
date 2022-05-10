package com.example.back.service;

import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.models.entities.User;
import com.example.back.repositories.MatchRepo;
import com.example.back.repositories.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<MatchEntity> getFavoriteMatchesByUserId(Long id) {
        if (userRepo.findById(id).isPresent()) {
            return new ArrayList<>(userRepo.findById(id).get().getFavoriteMatches());
        }
        return null;
    }

    @Override
    public List<Team> getFavoriteTeamsByUserId(Long id) {
        if (userRepo.findById(id).isPresent()) {
            return new ArrayList<>(userRepo.findById(id).get().getFavoriteTeams());
        }
        return null;
    }

    @Override
    public List<League> getFavoriteLeaguesByUserId(Long id) {
        if (userRepo.findById(id).isPresent()) {
            return new ArrayList<>(userRepo.findById(id).get().getFavoriteLeagues());
        }
        return null;
    }
}
