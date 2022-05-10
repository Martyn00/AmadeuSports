package com.example.back.service;

import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.repositories.TeamRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepo teamRepo;

    @Override
    public List<MatchEntity> getMatchesHistory(Long id) {
        if (teamRepo.findById(id).isPresent()) {
            return new ArrayList<>(teamRepo.findById(id).get().getMatches());
        }
        return null;
    }
}
