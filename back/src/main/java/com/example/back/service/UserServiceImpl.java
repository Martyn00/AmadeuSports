package com.example.back.service;

import com.example.back.controllers.dto.UserDto;
import com.example.back.handlers.*;
import com.example.back.models.entities.*;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public ResponseEntity<ArrayList<UserDto>> getAllUsers() {
        ArrayList<UserDto> result = new ArrayList<>();

        List<User> users = userRepo.findAll();

        for(User user : users) {
            result.add(new UserDto(user.getId(), user.getUsername()));
        }

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<String> addFriend(Long friend_id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long me_id = ((User)principal).getId();
            if(!Objects.equals(me_id, friend_id)) {
                User me = userRepo.findById(me_id).orElseThrow(() -> {
                    throw new UserNotFoundException();
                });
                User friend = userRepo.findById(friend_id).orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

                if (me.getFriends().contains(friend)) {
                    throw new YouAreAlreadyFriendsException();
                }

                me.getFriends().add(friend);
                userRepo.save(me);
                return ResponseEntity.ok("You are now friends!");
            }
            throw new FriendWithYourselfException();
        }
        throw new NotLoggedInException();
    }

    @Override
    public ResponseEntity<String> removeFriend(Long friend_id) {
        User me = getCurrentUserInstance();
        User friend = userRepo.findById(friend_id).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        if (me.getFriends().contains(friend)) {
            me.getFriends().remove(friend);
            userRepo.save(me);
            return ResponseEntity.ok("User " + friend.getUsername() + " removed from friends!");
        }

        throw new NotFriendsException();
    }

    @Override
    public ResponseEntity<String> addFriendByUserName(String userName) {
        User me = getCurrentUserInstance();

        if(!Objects.equals(me.getUsername(), userName)) {

            User friend = userRepo.findByUsername(userName).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            if (me.getFriends().contains(friend)) {
                throw new YouAreAlreadyFriendsException();
            }

            me.getFriends().add(friend);
            userRepo.save(me);
            return ResponseEntity.ok("You are now friends!");
        }
        throw new FriendWithYourselfException();
    }

    @Override
    public ResponseEntity<String> removeFriendByUserName(String userName) {
        User me = getCurrentUserInstance();
        User friend = userRepo.findByUsername(userName).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        if (me.getFriends().contains(friend)) {
            me.getFriends().remove(friend);
            userRepo.save(me);
            return ResponseEntity.ok("User " + friend.getUsername() + " removed from friends!");
        }

        throw new NotFriendsException();
    }

    @Override
    public ResponseEntity<ArrayList<UserDto>> getAllFriends() {
        ArrayList<UserDto> result = new ArrayList<>();

        User me = getCurrentUserInstance();

        for (User user : me.getFriends()) {
            result.add(new UserDto(user.getId(), user.getUsername()));
        }

        return ResponseEntity.ok(result);
    }

    public User getCurrentUserInstance() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long userId = ((User) principal).getId();
            return userRepo.findById(userId).orElseThrow(() -> {
                throw new UserNotFoundException();
            });
        }
        throw new NotLoggedInException();
    }

    @Override
    public ResponseEntity<UserDto> getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long me_id = ((User) principal).getId();
            User me = userRepo.findById(me_id).orElseThrow(() -> {
                throw new UserNotFoundException();
            });
            UserDto userDto = new UserDto();
            userDto.setId(me.getId());
            userDto.setUsername(me.getUsername());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        throw new NotLoggedInException();
    }

    public ResponseEntity<Integer> getUserWallet() {
        User user = getCurrentUserInstance();
        return ResponseEntity.ok(user.getWallet());
    }
}
