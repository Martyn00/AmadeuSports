package com.example.back.controllers;

import com.example.back.controllers.dto.UserDto;
import com.example.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/AmadeusSports/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @GetMapping(path= "/all")
    public ResponseEntity<ArrayList<UserDto>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/friends")
    public ResponseEntity<ArrayList<UserDto>> getAllFriends() {
        return userService.getAllFriends();
    }

    @PostMapping(path = "/add-friend/{userId}")
    public ResponseEntity<String> addFriend(@PathVariable Long userId) {
        return userService.addFriend(userId);
    }

    @PostMapping(path = "/remove-friend/{userId}")
    public ResponseEntity<String> removeFriend(@PathVariable Long userId) {
        return userService.removeFriend(userId);
    }

    @PostMapping(path = "/add-friend-by-username/{userName}")
    public ResponseEntity<String> addFriendbyUserName(@PathVariable String userName) {
        return userService.addFriendByUserName(userName);
    }

    @PostMapping(path = "/remove-friend-by-username/{userName}")
    public ResponseEntity<String> removeFriendByUserName(@PathVariable String userName) {
        return userService.removeFriendByUserName(userName);
    }

}
