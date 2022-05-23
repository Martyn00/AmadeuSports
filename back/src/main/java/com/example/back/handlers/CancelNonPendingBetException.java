package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="You can't cancel an outgoing bet!")  // 400
public class CancelNonPendingBetException extends RuntimeException {
}
