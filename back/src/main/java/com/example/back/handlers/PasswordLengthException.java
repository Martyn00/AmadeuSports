package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Password must be at least 6 characters long!")  // 400
public class PasswordLengthException extends RuntimeException{
}
