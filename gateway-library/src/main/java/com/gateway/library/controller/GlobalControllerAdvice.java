package com.gateway.library.controller;

import com.gateway.library.exception.UserNotAuthenticateException;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(WebExchangeBindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        log.info("Peticion con campos invalidos");

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UserNotAuthenticateException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleUserNotAuthenticateException(UserNotAuthenticateException e) {
        log.warn("Error de autenticacion: {}", e.getMessage());
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException e) {
        log.warn("Conflicto con recurso existente: {}", e.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
