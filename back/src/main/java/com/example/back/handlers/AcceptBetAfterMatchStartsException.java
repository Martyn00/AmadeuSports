package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Too late! Match had already started. Refresh bets to show outgoing bets.")  // 400
public class AcceptBetAfterMatchStartsException extends RuntimeException{
}
