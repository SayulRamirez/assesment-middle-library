package com.loan.microservice.controller;

import com.loan.microservice.exception.DocumentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> response = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(
                (error) -> {

                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();

                    response.put(fieldName, message);
                }
        );

        response.put("timestamp: ", LocalDateTime.now());
        response.put("code: ", status.toString());

        log.warn("Peticion con campos invalidos");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleDocumentNotFoundException(DocumentNotFoundException e) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("message", e.getMessage());
        response.put("timestamp", LocalDateTime.now());
        response.put("code", HttpStatus.NOT_FOUND);

        log.warn("Excepcion documento: {}", e.getMessage());

        return response;
    }
}
