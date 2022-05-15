package com.example.back.service;

import com.example.back.controller.dto.UserDto;
import com.example.back.models.entities.*;
import com.example.back.repositories.FriendsRepo;
import com.example.back.repositories.MatchRepo;
import com.example.back.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final FriendsRepo friendsRepo;

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
    public ArrayList<UserDto> getAllUsers() {
        ArrayList<UserDto> result = new ArrayList<>();

        List<User> users = userRepo.findAll();

        for(User user : users) {
            result.add(new UserDto(user.getId(), user.getUsername()));
        }

        return result;
    }

    @Override
    public String addFriend(Long friend_id) {
        System.out.println(friend_id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal.toString());
        if (principal instanceof UserDetails) {
            Long me_id = ((User)principal).getId();
            if(!Objects.equals(me_id, friend_id)) {
                Optional<User> me = userRepo.findById(me_id);
                Optional<User> friend = userRepo.findById(friend_id);

                if(me.isPresent() && friend.isPresent()) {
                    Optional<Friends> isRelation = friendsRepo.findByMeAndFriends(me.get(), friend.get());
                    if(isRelation.isEmpty()) {
                        friendsRepo.save(new Friends(me.get(), friend.get()));
                        return "you are friends now";
                    }
                    return "you are already friends";
                }
                return "one of the users don't exist";
            }
            return "you cannot be friends with yourself";
        }
        return "you are not logged in";
    }
}
