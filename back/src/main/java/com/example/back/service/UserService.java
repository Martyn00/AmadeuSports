package com.example.back.service;

import com.example.back.controller.dto.UserDto;
import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

    List<MatchEntity> getFavoriteMatchesByUserId(Long id);

    List<Team> getFavoriteTeamsByUserId(Long id);

    List<League> getFavoriteLeaguesByUserId(Long id);

    ArrayList<UserDto> getAllUsers();

    String addFriend(Long id);
}
