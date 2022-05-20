package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="You can't be friend with yourself!")  // 400
public class FriendWithYourselfException extends RuntimeException{
}
