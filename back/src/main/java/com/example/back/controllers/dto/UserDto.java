package com.example.back.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    Long id;
    String username;

    public UserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
