package com.example.back.repositories;

import com.example.back.models.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepo extends JpaRepository<League, Long> {
    Optional<League> findByName(String name);

    Optional<League> findByCountry(String country);
}
