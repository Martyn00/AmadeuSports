package com.example.back.models.requestsAndResponses;

import com.example.back.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
