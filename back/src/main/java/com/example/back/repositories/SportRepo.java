package com.example.back.repositories;

import com.example.back.models.entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SportRepo extends JpaRepository<Sport, Long> {
    Optional<Sport> findByName(String name);
}
