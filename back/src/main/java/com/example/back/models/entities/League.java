package com.example.back.models.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @ToString.Exclude
    private Set<Team> teams = new HashSet<>();

    @OneToMany
    @ToString.Exclude
    private Set<MatchEntity> matches = new HashSet<>();

    private String name;
    private String country;
    @ManyToOne
    private Sport sport;
}
