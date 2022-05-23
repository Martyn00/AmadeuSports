package com.example.back.repositories;

import com.example.back.models.entities.MatchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchEventRepo extends JpaRepository<MatchEvent, Long> {
}
