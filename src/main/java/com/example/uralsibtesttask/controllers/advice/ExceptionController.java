package com.example.uralsibtesttask.controllers.advice;

import com.example.uralsibtesttask.Exceptions.AbstractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(AbstractException.class)
    public ResponseEntity<String> handleBadCredentialsException(AbstractException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
