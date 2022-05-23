package com.example.back.service;

import com.example.back.controllers.dto.BetDto;
import com.example.back.controllers.dto.UserDto;
import com.example.back.models.entities.Bet;
import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

    List<MatchEntity> getFavoriteMatchesByUserId(Long id);

    List<Team> getFavoriteTeamsByUserId(Long id);

    List<League> getFavoriteLeaguesByUserId(Long id);

    ResponseEntity<Void> addMatchToFavorites(Long userId, Long matchId);

    ArrayList<UserDto> getAllUsers();

    ResponseEntity<String> addFriend(Long id);

    ResponseEntity<String> removeFriend(Long id);

    ResponseEntity<String> addFriendByUserName(String userName);
//
    ResponseEntity<String> removeFriendByUserName(String userName);

    ArrayList<UserDto> getAllFriends();
}
