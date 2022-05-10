package com.example.back.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "league_id")
    private Set<Team> teams;

    @OneToMany
    @JoinColumn(name = "league_id")
    private Set<MatchEntity> matches;
}
