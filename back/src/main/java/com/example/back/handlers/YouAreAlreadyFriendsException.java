package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="You are already friends!")  // 400
public class YouAreAlreadyFriendsException extends RuntimeException{
}
