package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Bet not in pending list and can't be accepted!")  // 400
public class NotInUserBetListException extends RuntimeException{
}
