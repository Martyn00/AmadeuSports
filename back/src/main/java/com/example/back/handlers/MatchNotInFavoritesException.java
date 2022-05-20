package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Match not in favorite list!")  // 400
public class MatchNotInFavoritesException extends RuntimeException{
}
