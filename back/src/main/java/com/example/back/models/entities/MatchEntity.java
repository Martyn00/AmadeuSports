package com.example.back.models.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    @OneToOne
    private League league;

    @OneToMany
    private Set<MatchEvent> events;

    private String status;
    private String result;
    private LocalDateTime startTime;
    private String data;
    private Integer duration;

    public MatchEntity(Team team1, Team team2, League league, Set<MatchEvent> events, String status, String result, LocalDateTime startTime, String data, Integer duration) {
        this.team1 = team1;
        this.team2 = team2;
        this.league = league;
        this.events = events;
        this.status = status;
        this.result = result;
        this.startTime = startTime;
        this.data = data;
        this.duration = duration;
    }
}
