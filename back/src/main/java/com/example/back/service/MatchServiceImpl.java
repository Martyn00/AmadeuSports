package com.example.back.service;

import com.example.back.models.entities.MatchEntity;
import com.example.back.repositories.MatchRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Service
public class MatchServiceImpl implements MatchService{

    private final MatchRepo matchRepo;

    @Override
    public List<MatchEntity> getMatchByDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
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
