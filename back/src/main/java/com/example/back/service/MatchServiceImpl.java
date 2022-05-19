package com.example.back.service;

import com.example.back.controllers.dto.MatchDto;
import com.example.back.models.entities.MatchEntity;
import com.example.back.repositories.MatchRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepo matchRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<MatchDto> getMatchByDate(Integer numberOfDays) {

        LocalDateTime dateTime = LocalDateTime.now().plusDays(numberOfDays);

        List<MatchEntity> matches = new LinkedList<>();
        for (MatchEntity match : matchRepo.findAll()) {
            if (match.getStartTime().getYear() == dateTime.getYear() &&
                    match.getStartTime().getMonth() == dateTime.getMonth() &&
                    match.getStartTime().getDayOfMonth() == dateTime.getDayOfMonth()) {
                matches.add(match);
            }
        }

        return matches.stream()
                .map(this::mapToMatchDto).collect(Collectors.toList());
    }

    private String getWinner(MatchEntity match) {
        if (!match.isUpcoming()) {
            String[] arrOfGoals = match.getResult().split("-");
            if (Integer.parseInt(arrOfGoals[0]) > Integer.parseInt(arrOfGoals[1])) {
                return match.getTeam1().getName();
            }
            if (Integer.parseInt(arrOfGoals[0]) < Integer.parseInt(arrOfGoals[1])) {
                return match.getTeam2().getName();
            }

            return "Draw";
        }

        return "Upcoming";
    }

    private MatchDto mapToMatchDto(MatchEntity matchEntity) {
        MatchDto matchDto = modelMapper.map(matchEntity, MatchDto.class);
        matchDto.setLeague(matchEntity.getLeague().getName());
        matchDto.setCountry(matchEntity.getTeam1().getCountry());
        matchDto.setTeam1(matchEntity.getTeam1().getName());
        matchDto.setTeam2(matchEntity.getTeam2().getName());
        matchDto.setDetails(matchEntity.getData());
        matchDto.setScore(matchEntity.getResult());
        matchDto.setIsFavorite(false);
        matchDto.setSport("football");
        matchDto.setWinner(getWinner(matchEntity));

        matchDto.setTime(matchEntity.getStartTime().getHour() + ":" + matchEntity.getStartTime().getMinute() );
        return matchDto;
    }
}
