package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="You can't accept a non pending bet!")  // 400
public class AcceptNonPendingBetException extends RuntimeException{
}
