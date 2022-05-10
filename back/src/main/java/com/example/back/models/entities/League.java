package com.example.back.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private Set<Team> teams = new HashSet<>();

    @OneToMany
    private Set<MatchEntity> matches = new HashSet<>();

    private String name;
    private String country;
}
