package com.example.back.service;

import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.repositories.TeamRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepo teamRepo;

    public TeamServiceImpl(TeamRepo teamRepo) {
        this.teamRepo = teamRepo;
    }

    @Override
    public List<MatchEntity> getMatchesHistory(Long id) {
        if (teamRepo.findById(id).isPresent()) {
            return new ArrayList<>(teamRepo.findById(id).get().getMatches());
        }
        return null;
    }
}
