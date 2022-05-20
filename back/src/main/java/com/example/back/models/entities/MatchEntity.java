package com.example.back.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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

    private boolean isUpcoming;
    private String result;
    private LocalDateTime startTime;
    private String data;
}
