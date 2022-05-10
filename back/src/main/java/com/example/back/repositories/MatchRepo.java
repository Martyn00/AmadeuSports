package com.example.back.repositories;

import com.example.back.models.entities.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepo extends JpaRepository<MatchEntity, Long> {
}
