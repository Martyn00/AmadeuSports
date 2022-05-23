package com.example.back.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBetChoiceDto {
    private Long user1Id;
    private Long user2Id;
    private int user1Choice;
    private int user2Choice;
}
