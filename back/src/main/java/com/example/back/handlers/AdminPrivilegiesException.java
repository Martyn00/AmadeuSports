package com.example.back.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Only admins can complete this action!")  // 400
public class AdminPrivilegiesException extends RuntimeException {
}
