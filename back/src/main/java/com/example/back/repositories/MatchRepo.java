package com.example.back.repositories;

import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepo extends JpaRepository<MatchEntity, Long> {
    Optional<MatchEntity> findById(Long id);
}
