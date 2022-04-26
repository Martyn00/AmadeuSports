package com.example.back.models.requestsAndResponses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AuthRequest {
    private String username;
    private String password;
}
