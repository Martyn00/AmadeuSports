package com.example.back.service;

import com.example.back.controllers.dto.BetDto;
import com.example.back.controllers.dto.MatchDto;
import com.example.back.controllers.dto.UserDto;
import com.example.back.models.entities.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

    ResponseEntity<ArrayList<UserDto>> getAllUsers();

    ResponseEntity<String> addFriend(Long id);

    ResponseEntity<String> removeFriend(Long id);

    ResponseEntity<String> addFriendByUserName(String userName);
//
    ResponseEntity<String> removeFriendByUserName(String userName);

    ResponseEntity<ArrayList<UserDto>> getAllFriends();
    User getCurrentUserInstance();
}
