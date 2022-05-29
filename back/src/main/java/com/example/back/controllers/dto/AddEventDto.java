package com.example.back.controllers.dto;

import com.example.back.models.entities.GoalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddEventDto {
    private Long matchId;
    private GoalType goal;
    private Integer minute;
}
