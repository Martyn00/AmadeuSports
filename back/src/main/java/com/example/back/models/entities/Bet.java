package com.example.back.models.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private MatchEntity match;

    @ManyToOne
    private User user1;
    @ManyToOne
    private User user2;

    private BetType betChoiceUser1;
    private BetType betChoiceUser2;
    private int amount;
    private String status;
    private int result;

    public Bet(MatchEntity match, User user1, User user2, BetType betChoiceUser1, BetType betChoiceUser2, int amount, String status, int result) {
        this.match = match;
        this.user1 = user1;
        this.user2 = user2;
        this.betChoiceUser1 = betChoiceUser1;
        this.betChoiceUser2 = betChoiceUser2;
        this.amount = amount;
        this.status = status;
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bet bet = (Bet) o;
        return id != null && Objects.equals(id, bet.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
