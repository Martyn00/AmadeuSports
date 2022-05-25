package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="You can't bet on an outgoing or finished match!")  // 400
public class OutgoingMatchBetException extends RuntimeException{
}
