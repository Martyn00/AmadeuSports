package com.example.back.controllers.dto;

import com.example.back.models.entities.BetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BetDto {
    MatchDto match;
    BetType betChoiceUser1;
    BetType betChoiceUser2;
    UserDto user1;
    UserDto user2;
}
