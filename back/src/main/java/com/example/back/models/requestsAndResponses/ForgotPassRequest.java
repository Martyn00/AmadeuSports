package com.example.back.models.requestsAndResponses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ForgotPassRequest {
    private String username;
    private String email;
    private String old_password;
    private String new_password;
}
