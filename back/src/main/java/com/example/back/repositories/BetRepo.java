package com.example.back.repositories;

import com.example.back.models.entities.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepo extends JpaRepository<Bet, Long> {
}
