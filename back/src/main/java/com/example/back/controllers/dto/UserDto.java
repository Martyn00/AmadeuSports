package com.example.back.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    Long id;
    String username;

    public UserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
