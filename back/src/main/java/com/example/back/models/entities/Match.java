package com.example.back.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
//    @JoinColumn(name = "team1_id")
    private Team team1;

    @OneToOne
//    @JoinColumn(name = "team2_id")
    private Team team2;

    private boolean isUpcoming;
    private String result;
    private LocalDateTime startTime;
    private String data;
}
