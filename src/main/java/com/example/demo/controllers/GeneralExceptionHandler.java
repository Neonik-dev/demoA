package com.example.demo.controllers;

import com.example.demo.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.Arrays;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, SQLException.class})
    protected ResponseEntity<ErrorResponse> handleConflict(Exception exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                Arrays.toString(exception.getStackTrace())
        ));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse> handleConflict(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                exception.getBindingResult().getFieldError().getDefaultMessage()
        ));
    }
}
