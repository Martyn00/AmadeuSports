package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The email does not have the right format!")  // 400
public class WrongEmailFormatException extends RuntimeException{
}
