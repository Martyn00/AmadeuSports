package com.example.back.repositories;

import com.example.back.models.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepo extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
}
