package com.arcaneemporium.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> HandleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> messages = new ArrayList<>();

        for(FieldError fieldError: e.getFieldErrors()){
            messages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(messages);
    }
}
