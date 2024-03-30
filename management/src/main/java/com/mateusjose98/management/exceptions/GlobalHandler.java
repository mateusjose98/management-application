package com.mateusjose98.management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(UserAuthException.class)
    public ResponseEntity<?> handleUserAuthException(UserAuthException e) {
        Map<String, String> error = Map.of("error", e.getMessage());
        error.put("status", "401");
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
