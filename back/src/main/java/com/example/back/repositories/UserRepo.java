package com.example.back.repositories;

import com.example.back.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    @Modifying
    @Query("update User a set a.confirmed = true where a.username = ?1")
    void  updateConfirmUser(String username);

    @Modifying
    @Query("update User a set a.password = ?2 where a.username = ?1")
    void updatePassword(String username, String password);
}
