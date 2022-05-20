package com.example.back.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TeamDto {
    private String name;
    private Long id;
    private Boolean isFavorite;
}
