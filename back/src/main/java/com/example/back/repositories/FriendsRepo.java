package com.example.back.repositories;

import com.example.back.models.entities.Friends;
import com.example.back.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendsRepo extends JpaRepository<Friends, Long> {

    Optional<Friends> findByMeAndFriends(User me, User friend);

}
