package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Team not in favorites!")  // 400
public class TeamNotInFavoritesException extends RuntimeException{
}
