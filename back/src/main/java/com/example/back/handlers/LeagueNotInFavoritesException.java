package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="League is not in favorites list!")  // 400
public class LeagueNotInFavoritesException extends RuntimeException{
}
