package com.example.back.models.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
