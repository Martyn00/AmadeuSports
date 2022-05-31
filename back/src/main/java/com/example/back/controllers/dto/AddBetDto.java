package com.example.back.controllers.dto;

import com.example.back.models.entities.BetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddBetDto {
    private Long matchId;
    private String username;
    private BetType betType;
    private Integer amount;
}
