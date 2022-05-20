package com.example.back.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LeagueDto {
    private String name;
    private Long id;
    private Boolean isFavorite;
}
