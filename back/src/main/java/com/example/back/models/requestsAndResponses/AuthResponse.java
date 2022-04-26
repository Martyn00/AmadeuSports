package com.example.back.models.requestsAndResponses;

import com.example.back.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthResponse {
    private final String jwt;
    private final String username;
    private final Role role;
}
