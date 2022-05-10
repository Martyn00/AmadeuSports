package com.example.back.controller;

import com.example.back.models.entities.League;
import com.example.back.models.entities.MatchEntity;
import com.example.back.models.entities.Team;
import com.example.back.models.requestsAndResponses.AuthRequest;
import com.example.back.models.requestsAndResponses.ForgotPassRequest;
import com.example.back.models.requestsAndResponses.RegisterRequest;
import com.example.back.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/AmadeusSports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {

    private final RegisterService registerService;
    private final LoginService loginService;




    @PostMapping(path = "/register")
    public String registerNewUser(@RequestBody RegisterRequest registerRequest) {
        return registerService.registerNewUser(registerRequest);
    }

    @GetMapping(path = "/confirmToken")
    public void confirmToken(String token) {
        registerService.confirmToken(token);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        return loginService.tryLogin(authenticationRequest);
    }

    @PostMapping(path = "/forgotPassword")
    public String forgotPassword(@RequestBody ForgotPassRequest forgotPassRequest) {
        return loginService.forgotPassword(forgotPassRequest);
    }

}
