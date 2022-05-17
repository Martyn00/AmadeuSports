package com.example.back.service;

import com.example.back.controllers.dto.MatchDto;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.User;
import com.example.back.repositories.MatchRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepo matchRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;

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

    @Override
    public String addMatchToFavorites(Long matchID) {
        Optional<MatchEntity> match = matchRepo.findById(matchID);

        if(match.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal instanceof UserDetails) {
                    Optional<User> user = userRepo.findById(((User) principal).getId());

                    if(user.isPresent()) {
                        if(!user.get().getFavoriteMatches().contains(match.get())) {
                            user.get().getFavoriteMatches().add(match.get());
                            userRepo.save(user.get());
                            return "The match " + matchID + " has been added as favorites to user " + user.get().getUsername();
                        }
                        return "The match is already at favorites";
                    }
                    return "The user does not exist";
            }
            return "You are not logged in";
        }
        return "The match does not exist";
    }

    @Override
    public String removeMatchFromFavorites(Long matchID) {
        Optional<MatchEntity> match = matchRepo.findById(matchID);

        if(match.isPresent()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(principal instanceof UserDetails) {
                Optional<User> user = userRepo.findById(((User) principal).getId());

                if(user.isPresent()) {
                    if(user.get().getFavoriteMatches().contains(match.get())) {
                        user.get().getFavoriteMatches().remove(match.get());
                        userRepo.save(user.get());
                        return "The match " + matchID + " has been removed from favorites from user " + user.get().getUsername();
                    }
                    return "The match is not at favorites";
                }
                return "The user does not exist";
            }
            return "You are not logged in";
        }
        return "The match does not exist";
    }

    private MatchDto mapToMatchDto(MatchEntity matchEntity) {
        MatchDto matchDto = modelMapper.map(matchEntity, MatchDto.class);
//        findLeagueByteam
        matchDto.setCountry(matchEntity.getTeam1().getCountry());
        matchDto.setTeam1(matchEntity.getTeam1().getName());
        matchDto.setTeam2(matchEntity.getTeam2().getName());
        matchDto.setDetails(matchEntity.getData());
        matchDto.setScore(matchEntity.getResult());
        matchDto.setIsFavorite(false);
        matchDto.setSport("football");

        matchDto.setTime(matchEntity.getStartTime().getHour() + ":" + matchEntity.getStartTime().getMinute() );
        return matchDto;
    }
}
