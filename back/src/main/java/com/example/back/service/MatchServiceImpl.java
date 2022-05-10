package com.example.back.service;

import com.example.back.models.entities.MatchEntity;
import com.example.back.repositories.MatchRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService{

    private final MatchRepo matchRepo;

    public MatchServiceImpl(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }

    @Override
    public List<MatchEntity> getMatchByDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime dateTime = LocalDateTime.parse(data, formatter);

        List<MatchEntity> matches = new LinkedList<>();
        for (MatchEntity match : matchRepo.findAll()) {
            if (match.getStartTime().getYear() == dateTime.getYear() &&
            match.getStartTime().getMonth() == dateTime.getMonth() &&
            match.getStartTime().getDayOfMonth() == dateTime.getDayOfMonth()) {
                matches.add(match);
            }
        }

        return matches;
    }
}
