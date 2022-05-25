package com.example.back.controllers;

import com.example.back.controllers.dto.UserDto;
import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/AmadeusSports/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}/favorite-matches")
    public List<MatchEntity> getFavoriteMatchesByUserId(@PathVariable Long id) {
        return userService.getFavoriteMatchesByUserId(id);
    }

    @GetMapping(path = "/{id}/favorite-teams")
    public List<Team> getFavoriteTeamsByUserId(@PathVariable Long id) {
        return userService.getFavoriteTeamsByUserId(id);
    }

    @GetMapping(path = "/{id}/favorite-leagues")
    public List<League> getFavoriteLeaguesByUserId(@PathVariable Long id) {
        return userService.getFavoriteLeaguesByUserId(id);
    }

    @GetMapping(path= "/all")
    public ArrayList<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/friends")
    public ArrayList<UserDto> getAllFriends() {
        return userService.getAllFriends();
    }

    @PostMapping(path = "/{userId}/add-friend")
    public ResponseEntity<String> addFriend(@PathVariable Long userId) {
        return userService.addFriend(userId);
    }

    @PostMapping(path = "/{userId}/remove-friend")
    public ResponseEntity<String> removeFriend(@PathVariable Long userId) {
        return userService.removeFriend(userId);
    }

    @PostMapping(path = "/{userName}/add-friend-by-username")
    public ResponseEntity<String> addFriendbyUserName(@PathVariable String userName) {
        return userService.addFriendByUserName(userName);
    }

    @PostMapping(path = "/{userName}/remove-friend-by-username")
    public ResponseEntity<String> removeFriendByUserName(@PathVariable String userName) {
        return userService.removeFriendByUserName(userName);
    }

    @GetMapping(path = "/logged")
    public ResponseEntity<UserDto> getLoggedInUser() {
        return userService.getLoggedInUser();
    }

}
