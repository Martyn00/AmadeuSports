package com.example.back.models.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Friends {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name="me_id")
    private User me;

    @ManyToOne
    @JoinColumn(name="friends_id")
    private User friends;

    public Friends(User me, User friends) {
        this.me = me;
        this.friends = friends;
    }
}
