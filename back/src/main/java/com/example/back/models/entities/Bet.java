package com.example.back.models.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private MatchEntity match;

    @OneToOne
    private User user1;
    @OneToOne
    private User user2;

    public BetType betChoiceUser1;
    public BetType betChoiceUser2;
}
