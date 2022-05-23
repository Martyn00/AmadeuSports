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
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @ToString.Exclude
    private Set<MatchEntity> matches = new HashSet<>();

    @ManyToOne
    private League homeLeague;

    private String name;
    private String country;
}
