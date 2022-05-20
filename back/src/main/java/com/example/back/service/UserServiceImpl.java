package com.example.back.service;

import com.example.back.controllers.dto.UserDto;
import com.example.back.handlers.*;
import com.example.back.models.entities.*;
import com.example.back.repositories.FriendsRepo;
import com.example.back.repositories.MatchRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final FriendsRepo friendsRepo;
    private final MatchRepo matchRepo;

    @Override
    public List<MatchEntity> getFavoriteMatchesByUserId(Long id) {
        if (userRepo.findById(id).isPresent()) {
            return new ArrayList<>(userRepo.findById(id).get().getFavoriteMatches());
        }
        return null;
    }

    @Override
    public List<Team> getFavoriteTeamsByUserId(Long id) {
        if (userRepo.findById(id).isPresent()) {
            return new ArrayList<>(userRepo.findById(id).get().getFavoriteTeams());
        }
        return null;
    }

    @Override
    public List<League> getFavoriteLeaguesByUserId(Long id) {
        if (userRepo.findById(id).isPresent()) {
            return new ArrayList<>(userRepo.findById(id).get().getFavoriteLeagues());
        }
        return null;
    }

    @Override
    public ResponseEntity<Void> addMatchToFavorites(Long userId, Long matchId) {
        User user =  userRepo.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        MatchEntity match =  matchRepo.findById(matchId).orElseThrow(() -> {
            throw new MatchNotFoundException();
        });

        if (user.getFavoriteMatches().contains(match)) {
            throw new MatchAlreadyExistsInFavoritesException();
        }

        user.getFavoriteMatches().add(match);
        userRepo.save(user);
        return ResponseEntity.ok(null);
    }

    @Override
    public ArrayList<UserDto> getAllUsers() {
        ArrayList<UserDto> result = new ArrayList<>();

        List<User> users = userRepo.findAll();

        for(User user : users) {
            result.add(new UserDto(user.getId(), user.getUsername()));
        }

        return result;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Long me_id = ((User)principal).getId();

            User me = userRepo.findById(me_id).orElseThrow(() -> {
                throw new UserNotFoundException();
            });
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
        throw new NotLoggedInException();
    }

    @Override
    public ArrayList<UserDto> getAllFriends() {
        ArrayList<UserDto> result = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long me_id = ((User) principal).getId();
            User me = userRepo.findById(me_id).orElseThrow(() -> {
                throw new UserNotFoundException();
            });

            for (User user : me.getFriends()) {
                result.add(new UserDto(user.getId(), user.getUsername()));
            }
            return result;
        }
        throw new NotLoggedInException();
    }
}
