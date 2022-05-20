package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="League not found!")  // 400
public class LeagueNotFoundException extends RuntimeException{
}
